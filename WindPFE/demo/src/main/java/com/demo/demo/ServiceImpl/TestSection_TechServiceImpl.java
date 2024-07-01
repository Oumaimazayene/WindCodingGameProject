package com.demo.demo.ServiceImpl;

import com.demo.demo.Repository.DomaineRepository;
import com.demo.demo.Repository.Question_Tech_Repository;
import com.demo.demo.Repository.TestSection_TechRepository;
import com.demo.demo.Service.Question_Tech_Service;
import com.demo.demo.Service.TestSectionTech_Service;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.dtos.TestSection_LogiqueDTo;
import com.demo.demo.dtos.TestSection_TechDTo;
import com.demo.demo.entity.*;
import com.demo.demo.mappers.Question_Tech_Mapper;
import com.demo.demo.mappers.TestSectionTechMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestSection_TechServiceImpl implements TestSectionTech_Service {
    private  final TestSection_TechRepository testSectionTechRepository;
    private final TestSectionTechMapper testSectionTechMapper;
    private  final Question_Tech_Repository questionTechRepository;
    private  final Question_Tech_Service questionTechService;
    private final DomaineRepository domaineRepository;

    @Override
    public Test_Section_Tech getTestSectionById(Long id) {
        return testSectionTechRepository.findById(id).orElse(null);
    }


    @Override
    public List<Test_Section_Tech> getTestSectionByUserUUID(UUID userUUID) {
        try {
            return testSectionTechRepository.findByUserUUID(userUUID);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des sections de test par l'UUID de l'utilisateur", e);
        }
    }


    @Override
    public List<TestSection_TechDTo> getAllTestSection() {
        return testSectionTechRepository.findAll().stream()
                .map(testSectionTechMapper::TEST_SECTION_TECH_D_TO)
                .collect(Collectors.toList());    }

    @Override
    public TestSection_TechDTo createTestSectionTech(TestSection_TechDTo testSectionTechDTo ,UUID user_uuid) {
        Test_Section_Tech testSectionTech = testSectionTechMapper.TEST_SECTION_TECH(testSectionTechDTo);
        List<Integer> privateNumber = testSectionTech.getPrivateNumber();
        List<Integer> publicNumber = testSectionTech.getPublicNumber();
        List<Long> domainIds = testSectionTechDTo.getDomain_id();
        List < Domaine> domains = domainIds.stream()
                .map(id -> domaineRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        int sum = calculateSum(publicNumber, privateNumber);
        if (sum > testSectionTech.getQtsNumber()) {
            throw new IllegalArgumentException("La somme des nombres publics et privés dépasse le nombre total de questions pour cette section de test.");
        }
        testSectionTech.setUserUUID(user_uuid);
        testSectionTech.setCreatedAt(new Date());
        testSectionTech.setDomain(domains);
        testSectionTech = testSectionTechRepository.save(testSectionTech);

        return testSectionTechMapper.TEST_SECTION_TECH_D_TO(testSectionTech);
    }



    @Override
    public TestSection_TechDTo updateTestSection(Long id, TestSection_TechDTo testSectionTechDTo) {
        Optional<Test_Section_Tech> existingTestSectionTech = testSectionTechRepository.findById(id);
        if (existingTestSectionTech.isPresent()) {
            testSectionTechMapper.updateTestSectionTechFromDTo(testSectionTechDTo, existingTestSectionTech.get());
        }
        return null;    }

    @Override
    public void deleteTestSectionTech(Long id) {
        try {
            testSectionTechRepository.deleteById(id);
            System.out.println("test_section Technique deleted successfully with ID:" + id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("test_Section Technique with  Id" + id + "not found");
            throw e;
        } catch (Exception e) {
            System.err.println("error deleting test_section");
            throw e;
        }

    }

    @Override
    public void deleteAllTestSectionTech() {
        try {
            testSectionTechRepository.deleteAll();
            System.out.println("All tests_section Technique deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all tests Section technique ");
            throw e;
        }
    }

    @Override
    public Question_Tech_DTo createPrivateQuestionTechnique(Question_Tech_DTo questionTechDTo, Long testSectionId) {
        Test_Section_Tech testSectionTech = testSectionTechRepository.findById(testSectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section de test non trouvée avec l'ID : " + testSectionId));
        questionTechDTo.setIsPrivate(true);
        Question_Tech_DTo createdQuestionTech = questionTechService.createQuestionTech(questionTechDTo);

        Question_Tech questionTechEntity = questionTechRepository.findById(createdQuestionTech.getId())
                .orElseThrow(() -> new IllegalArgumentException("Question logique non trouvée avec l'ID : " + createdQuestionTech.getId()));

        List<Question> currentQuestions = testSectionTech.getQuestions();
        if (currentQuestions == null) {
            currentQuestions = new ArrayList<>();
        }
        currentQuestions.add(questionTechEntity);
        testSectionTech.setQuestions(currentQuestions);
        testSectionTechRepository.save(testSectionTech);
        return createdQuestionTech;
    }


    public int calculateSum(List<Integer> publicNumber, List<Integer> privateNumber) {
        int sum = 0;
        if (publicNumber != null) {
            for (Integer number : publicNumber) {
                sum += number;
            }
        }
        if (privateNumber != null) {
            for (Integer number : privateNumber) {
                sum += number;
            }
        }
        return sum;
    }
    @Override
    public List<Question_Tech> getRandomTechnicalQuestionsByTestSectionId(Long testSectionId, Difficulty difficulty, String domain, int numberOfQuestions) {
        if (testSectionId == null) {
            throw new IllegalArgumentException("ID de section de test technique ne peut pas être null.");
        }

        Test_Section_Tech testSectionTech = testSectionTechRepository.findById(testSectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section de test technique non trouvée avec l'ID : " + testSectionId));

        List<Question> allQuestions = testSectionTech.getQuestions();
        if (allQuestions == null || allQuestions.isEmpty()) {
            return Collections.emptyList();
        }

        List<Question_Tech> filteredQuestions = allQuestions.stream()
                .filter(question -> question instanceof Question_Tech &&
                        question.getIsPrivate() &&
                        question.getDifficulty().equals(difficulty) &&
                        ((Question_Tech) question).getDomain().getName().equals(domain))
                .map(question -> (Question_Tech) question)
                .collect(Collectors.toList());

        if (filteredQuestions.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(filteredQuestions);
        int numQuestionsToReturn = Math.min(numberOfQuestions, filteredQuestions.size());
        return filteredQuestions.subList(0, numQuestionsToReturn);
    }

    @Override
    public List<Question_Tech> getPrivateQuestionsTechByTestSectionIdAll(Long testSectionId) {
        Test_Section_Tech testSection = testSectionTechRepository.findById(testSectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section de test non trouvée avec l'ID : " + testSectionId));

        List<Question> allQuestions = testSection.getQuestions();

        List<Question_Tech> privateQuestions = allQuestions.stream()
                .filter(question -> question instanceof Question_Tech && question.getIsPrivate())
                .map(question -> (Question_Tech) question)
                .collect(Collectors.toList());

        return privateQuestions;
    }


    @Override
    public int sommeQuestionsPriveesParUtilisateur(UUID userUUID) {
        List<Test_Section_Tech> testSectionTechList = testSectionTechRepository.findByUserUUID(userUUID);

        int sommeQuestionsPrivees = 0;

        for (Test_Section_Tech testSectionTech : testSectionTechList) {

            List<Question> questions = testSectionTech.getQuestions();
            int nombreQuestionsDansSection = questions != null ? questions.size() : 0;


            sommeQuestionsPrivees += nombreQuestionsDansSection;
        }

        return sommeQuestionsPrivees;
    }
    @Override
    public List<Test_Section_Tech> findByExperienceAndDifficulty(String experience, Difficulty difficulty) {
        return testSectionTechRepository.findByExperienceAndDifficulty(experience, difficulty);
    }

    public List<Question_Tech> getPrivateQuestionsByUserUUID(UUID userUUID) {
        List<Test_Section_Tech> testSections = testSectionTechRepository.findByUserUUID(userUUID);
        return testSections.stream()
                .flatMap(testSection -> testSection.getQuestions().stream())
                .filter(question -> question instanceof Question_Tech)
                .map(question -> (Question_Tech) question)
                .filter(Question_Tech::getIsPrivate)
                .collect(Collectors.toList());
    }
    @Transactional
    public void assignPrivateQuestionsToAnotherTestSection(List<Long> questionIds, Long targetTestSectionId) {
        Test_Section_Tech targetTestSection = testSectionTechRepository.findById(targetTestSectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section de test non trouvée avec l'ID : " + targetTestSectionId));
        List<Question_Tech> questionsToAssign = questionTechRepository.findAllById(questionIds).stream()
                .filter(Question_Tech::getIsPrivate)
                .collect(Collectors.toList());
        List<Question_Tech> alreadyAssignedQuestions = questionsToAssign.stream()
                .filter(targetTestSection.getQuestions()::contains)
                .collect(Collectors.toList());
        if (!alreadyAssignedQuestions.isEmpty()) {
            throw new IllegalArgumentException("Certaines questions sont déjà affectées à la section de test cible.");
        }
        targetTestSection.getQuestions().addAll(questionsToAssign);
        testSectionTechRepository.save(targetTestSection);
    }




}
