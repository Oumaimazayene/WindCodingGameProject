package com.demo.demo.ServiceImpl;

import com.demo.demo.Controller.AppController;
import com.demo.demo.Repository.*;
import com.demo.demo.Service.*;
import com.demo.demo.dtos.CandidateReponseDTo;
import com.demo.demo.dtos.TestDTo;
import com.demo.demo.entity.*;
import com.demo.demo.mappers.TestMapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final TestSectionRepository testSectionRepository;
    private final TestSectionService testSectionService;
    private final Question_Logique_Service questionLogiqueService;
    private final QuestionRepository questionRepository;
    private final Question_Tech_Service questionTechServices;
    private final TestSectionTech_Service testSectionTechService;
    private final CandidateService candidateService;
    private final EmailService emailService;
    private final Question_Tech_Repository questionTechRepository;
   private UserClient userClient;
   private ImageUploadService imageUploadService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public Test getTestById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    @Override
    public List<TestDTo> getAllTests() {
        return testRepository.findAll().stream()
                .map(testMapper::ToTestDto)
                .collect(Collectors.toList());
    }

    @Override
    public TestDTo updateTest(Long id, TestDTo testDTo) {
        Optional<Test> existingTest = testRepository.findById(id);
        if (existingTest.isPresent()) {
            testMapper.updateTestFromDTO(testDTo, existingTest.get());
            return testMapper.ToTestDto(testRepository.save(existingTest.get()));
        }
        return null;
    }

    @Override
    public void deleteTest(Long id) {
        try {
            Optional<Test> optionalTest = testRepository.findById(id);
            if (optionalTest.isPresent()) {
                Test test = optionalTest.get();
                test.setQuestions(new ArrayList<>());
                testRepository.save(test);
                testRepository.deleteById(id);

                System.out.println("Test deleted successfully with ID: " + id);
            } else {
                System.out.println("Test with ID " + id + " not found");
            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Test with ID " + id + " not found");
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting test with ID: " + id);
            throw e;
        }
    }

    @Override
    public void deleteAllTests() {
        try {
            List<Test> allTests = testRepository.findAll();

            for (Test test : allTests) {
                test.setQuestions(new ArrayList<>());
                testRepository.save(test);
            }
            testRepository.deleteAll();

            System.out.println("All Tests deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all tests");
            throw e;
        }
    }

    public <T extends Question> int calculateTotalScore(List<T> questions) {
        int totalScore = 0;

        for (T question : questions) {
            if (question.getScore() != null) {
                totalScore += question.getScore();
            }
        }
        return totalScore;
    }

    @Override
    public Test createLogicalTestLogic(UUID testSectionUUID, int size, int privateqts) {
        Test_Section testSection = testSectionRepository.findByUuid(testSectionUUID);
        if (testSection == null) {
            throw new IllegalArgumentException("Section de test non trouvée pour UUID: " + testSectionUUID);
        }
        int qtsNumber = testSection.getQtsNumber();
        if (size + privateqts != qtsNumber) {
            throw new IllegalArgumentException("La somme des questions publiques et privées doit être égale au nombre total de questions de la section de test.");
        }
        int totalTime = 0;

        Difficulty difficulty = testSection.getDifficulty();
        Long testSectionId = testSection.getId();

        List<Question_Logique> privateQuestions = testSectionService.getPrivateQuestionsByTestSectionId(testSectionId, difficulty.toString(), privateqts);
        List<Question_Logique> logicalQuestions = questionLogiqueService.getQuestionLogiqueByDifficultyAndIsNotPrivate(difficulty.toString(), size); // Modification ici pour passer la difficulté en tant que chaîne de caractères
        List<Question> combinedQuestions = new ArrayList<>(privateQuestions);
        combinedQuestions.addAll(logicalQuestions);

        if (combinedQuestions.size() > qtsNumber) {
            throw new IllegalArgumentException("La liste de questions dépasse la limite autorisée.");
        }
        for (Question question : combinedQuestions) {
            totalTime += question.getTime();
        }

        Test test = new Test();
        test.setQuestions(combinedQuestions);
        test.setCreatedAt(new Date());
        test.setTimeTotale(totalTime);
        test.setIsSubmitted(false);
        test.setScoreTotale(calculateTotalScore(combinedQuestions));
        test.setTestSectionUUID(testSectionUUID);
        testRepository.save(test);

        return test;
    }
    private boolean containsIgnoreCaseAndTrim(List<String> list, String value) {
        for (String item : list) {
            if (item.trim().equalsIgnoreCase(value.trim())) {
                return true;
            }
        }
        return false;
    }

    //    public boolean verifierReponse(Long questionId, CandidateReponseDTo candidateReponseDTO) {
//        if (!questionId.equals(candidateReponseDTO.getIdQuestion())) {
//            return false;
//        }
//        Optional<Question> questionOptional = questionRepository.findById(questionId);
//        if (questionOptional.isPresent()) {
//            Question question = questionOptional.get();
//            System.out.println("La question à vérifier est " + question);
//
//            List<String> candidateResponses = candidateReponseDTO.getReponses();
//
//            for (Answer answer : question.getAnswer()) {
//                System.out.println("IsTrue : " + answer.getIsTrue());
//                System.out.println("Réponse de la base de données : [" + answer.getAnswer() + "]");
//                if (answer.getIsTrue() && containsIgnoreCaseAndTrim(candidateResponses, answer.getAnswer())) {
//                    System.out.println("La réponse est correcte!");
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
    @Override
    public Test genererTestRapport(Long testId, List<CandidateReponseDTo> candidateReponseDTos) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new RuntimeException(" test  non trouvé avec l'id " + testId));
        test.setSubmittedDate(new Date());
        int correctAnswerCount = 0;
        int totalScore = 0;

        List<Question> questions = test.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            CandidateReponseDTo candidateReponseDTO = candidateReponseDTos.get(i);

            if (verifierReponse(question.getId(), candidateReponseDTO)) {
                for (Answer answer : question.getAnswer()) {
                    if (answer.getIsTrue() && candidateReponseDTO.getReponses().contains(answer.getAnswer())) {
                        correctAnswerCount++;
                        totalScore += answer.getQuestion().getScore();
                        break;
                    }
                }
            }
        }
        test.setCorrectAnswerCount(correctAnswerCount);
        test.setScorefinale(totalScore);
        double successRate = (double) correctAnswerCount / questions.size() * 100;
        test.setSuccessRate(successRate);
        test.setIsSubmitted(true);
        testRepository.save(test);


        return test;
    }

    @Override
    public Test createTestTechnique(UUID testSectionTechUUID) {
        Test_Section testSection = testSectionRepository.findByUuid(testSectionTechUUID);
        if (testSection == null) {
            throw new IllegalArgumentException("Section de test non trouvée pour UUID: " + testSectionTechUUID);
        }
        if (!(testSection instanceof Test_Section_Tech)) {
            throw new IllegalArgumentException("La section de test n'est pas une section de test technique");
        }
        Test_Section_Tech techTestSection = (Test_Section_Tech) testSection;

        if (!techTestSection.validateListsSize()) {
            throw new IllegalArgumentException("Les listes de la section de test n'ont pas la même taille");
        }
        int totalTime = 0;

        List<Question> combinedQuestions = new ArrayList<>();
        List<Difficulty> difficulties = techTestSection.getDifficulties();
        List<Integer> publicNumbers = techTestSection.getPublicNumber();
        List<Integer> privateNumbers = techTestSection.getPrivateNumber();
        List<Domaine> domains = techTestSection.getDomain();
        Long testSectionId = techTestSection.getId();
        Integer qtsNumber = techTestSection.getQtsNumber();

        for (int i = 0; i < difficulties.size(); i++) {
            Difficulty difficulty = difficulties.get(i);
            int publicNumber = publicNumbers.get(i);
            int privateNumber = privateNumbers.get(i);
            Domaine domain = domains.get(i);
            List<Question_Tech> publicQuestions = questionTechServices.getQuestionTechniqueByDifficultyAndIsNotPrivate(difficulty, domain.name, publicNumber);
            List<Question_Tech> privateQuestions = testSectionTechService.getRandomTechnicalQuestionsByTestSectionId(testSectionId, difficulty, domain.name, privateNumber);
            combinedQuestions.addAll(publicQuestions);
            combinedQuestions.addAll(privateQuestions);
            System.out.println("combine " + combinedQuestions);


        }

        if (combinedQuestions.size() > qtsNumber) {
            throw new IllegalArgumentException("La liste de questions dépasse la limite autorisée.");
        }
        for (Question question : combinedQuestions) {
            totalTime += question.getTime();
        }
        Test test = new Test();
        test.setQuestions(combinedQuestions);
        test.setCreatedAt(new Date());
        test.setTimeTotale(totalTime);
        test.setIsSubmitted(false);
        test.setScoreTotale(calculateTotalScore(combinedQuestions));
        test.setTestSectionUUID(testSectionTechUUID);
        testRepository.save(test);

        return test;
    }


    @Override
    public List<Test> getTestsByTestSectionUUID(UUID testSectionUUID) {
        return testRepository.findBytestSectionUUID(testSectionUUID);
    }

    @Override
    public void generateFreemarkerTestReport(Test test, List<CandidateReponseDTo> candidateReponseDTos) {
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("rapport.ftl");

            Map<String, Object> data = new HashMap<>();
            data.put("candidatefirstname", test.getCandidate().getFirstName());
            data.put("cadidatelastname", test.getCandidate().getLastName());
            data.put("scorefinal", test.getScoreTotale());
            data.put("correctCount", test.getCorrectAnswerCount());
            data.put("date", test.getSubmittedDate());
            data.put("email", test.getCandidate().getEmail());
            data.put("rate", test.getSuccessRate());
            data.put("Score", test.getScorefinale());
            data.put("questions", test.getQuestions());

            List<Boolean> reponseVerifiees = new ArrayList<>();

            for (Question question : test.getQuestions()) {
                CandidateReponseDTo candidateReponseDTO = findCandidateResponse(candidateReponseDTos, question.getId());
                boolean reponseVerifiee = verifierReponse(question.getId(), candidateReponseDTO);
                reponseVerifiees.add(reponseVerifiee);


            }

            data.put("reponseVerifiees", reponseVerifiees);
            String logoBase64 = imageUploadService.convertImageToBase64("C:\\Users\\DELL\\Desktop\\stage pfe\\WindHiring\\WindPFE\\demo\\src\\main\\resources\\static\\LogoWindERP.svg");
            data.put("logoBase64", logoBase64);
            File rapportFile = new File("rapport_" + test.getId() + ".html");
            try (Writer fileWriter = new FileWriter(rapportFile)) {
                template.process(data, fileWriter);
            }
           Test_Section testSection = testSectionRepository.findByUuid(test.getTestSectionUUID());
         UUID userUUID = testSection.getUserUUID();
          User createurSection = userClient.getUserByUUID(userUUID);
            emailService.sendEmailWithAttachment(test.getCandidate().getEmail(), "Rapport de test", "Veuillez trouver ci-joint le rapport de votre test.", rapportFile);
            String recruteurEmail = createurSection.getEmail();
           emailService.sendEmailWithAttachment(
                    recruteurEmail,
                    "Rapport de test du candidat",
                    "Veuillez trouver ci-joint le rapport de test du candidat.",
                    rapportFile
            );
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private CandidateReponseDTo findCandidateResponse(List<CandidateReponseDTo> candidateReponseDTos, Long questionId) {
        for (CandidateReponseDTo candidateResponse : candidateReponseDTos) {
            if (candidateResponse.getIdQuestion().equals(questionId)) {
                return candidateResponse;
            }
        }
        return null;
    }

    public boolean verifierReponse(Long questionId, CandidateReponseDTo candidateReponseDTO) {
        if (!questionId.equals(candidateReponseDTO.getIdQuestion())) {
            return false;
        }
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            System.out.println("La question à vérifier est " + question);

            return verifierReponseStandard(question, candidateReponseDTO);
        } else {
            return false;

        }
    }
    @Override
    public Test submitTest(Long id) {
        Optional<Test> optionalTest = testRepository.findById(id);
        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();
            test.setIsSubmitted(true);
            test.setSubmittedDate(new Date());
            return testRepository.save(test);
        } else {
            throw new RuntimeException("Test non trouvé avec l'id " + id);
        }
    }


    private boolean verifierReponseStandard(Question question, CandidateReponseDTo candidateReponseDTO) {
        List<String> candidateResponses = candidateReponseDTO.getReponses();
        for (Answer answer : question.getAnswer()) {
            System.out.println("IsTrue : " + answer.getIsTrue());
            System.out.println("Réponse de la base de données : [" + answer.getAnswer() + "]");
            if (answer.getIsTrue() && containsIgnoreCaseAndTrim(candidateResponses, answer.getAnswer())) {
                System.out.println("La réponse est correcte!");
                return true;
            }
        }
        return false;
    }

  /*  public boolean verifierReponseSiQuestionCode(Long questionId, CandidateReponseDTo candidateReponseDTO) {
        Optional<Question_Tech> questionOptional = questionTechRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            Question_Tech question = questionOptional.get();
            String language = question.getDomain().getLang();
            String version = question.getDomain().getVersion();
            String candidateCode = String.join("\n", candidateReponseDTO.getReponses());
            String stdin = null;
            ClientRunRequestBody requestBody = new ClientRunRequestBody(language, version, candidateCode, stdin);
            ResponseEntity<JdoodleResponseBody> responseEntity = appController.postRunRequest(requestBody);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                JdoodleResponseBody responseBody = responseEntity.getBody();
                String jdoodleOutput = responseBody.getOutput();

                for (Answer answer : question.getAnswer()) {
                    if (answer.getIsTrue() && answer.getAnswer().equals(jdoodleOutput)) {
                        return true;
                    }
                }
                return false;
            } else {
                System.out.println("Erreur lors de l'exécution du code du candidat. Code de statut : " + responseEntity.getStatusCode());
                return false;
            }
        } else {
            System.out.println("Question non trouvée dans la base de données!");
            return false;
        }
    }
*/


}




