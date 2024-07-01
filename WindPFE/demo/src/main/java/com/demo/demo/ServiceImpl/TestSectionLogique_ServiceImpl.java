package com.demo.demo.ServiceImpl;

import com.demo.demo.Repository.Question_Logique_Repository;
import com.demo.demo.Repository.TestSection_LogiqueRepository;
import com.demo.demo.Service.Question_Logique_Service;
import com.demo.demo.Service.TestSectionLogique_Service;
import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.dtos.TestSection_LogiqueDTo;
import com.demo.demo.entity.*;
import com.demo.demo.mappers.TestSectionLogiqueMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestSectionLogique_ServiceImpl implements TestSectionLogique_Service {
    private final TestSection_LogiqueRepository testSectionLogiqueRepository;
    private final TestSectionLogiqueMapper testSectionLogiqueMapper;
    private final Question_Logique_Service questionLogiqueService;
    private final Question_Logique_Repository questionLogiqueRepository;

    @Override
    public Test_Section_Logique getTestSectionById(Long id) {
        return testSectionLogiqueRepository.findById(id).orElse(null);

    }

    @Override
    public List<TestSection_LogiqueDTo> getTestSectionByUserUUID(UUID userUUID) {
        return testSectionLogiqueRepository.findByUserUUID(userUUID)
                .stream()
                .map(testSectionLogiqueMapper::TEST_SECTION_LOGIQUE_D_TO)
                .collect(Collectors.toList());
    }


    @Override
    public List<TestSection_LogiqueDTo> getAllTestSection() {
        return testSectionLogiqueRepository.findAll().stream()
                .map(testSectionLogiqueMapper::TEST_SECTION_LOGIQUE_D_TO)
                .collect(Collectors.toList());
    }

    @Override
    public TestSection_LogiqueDTo createTestSection(TestSection_LogiqueDTo testSectionLogiqueDTo, UUID userUUId) {
        Test_Section_Logique testSectionLogique = testSectionLogiqueMapper.TEST_SECTION_LOGIQUE(testSectionLogiqueDTo);
        testSectionLogique.setUserUUID(userUUId);
        return testSectionLogiqueMapper.TEST_SECTION_LOGIQUE_D_TO(testSectionLogiqueRepository.save(testSectionLogique)
        );
    }

    @Override
    public TestSection_LogiqueDTo updateTestSection(Long id, TestSection_LogiqueDTo testSectionLogiqueDTo) {
        Optional<Test_Section_Logique> existingTestSectionLog = testSectionLogiqueRepository.findById(id);
        if (existingTestSectionLog.isPresent()) {
            testSectionLogiqueMapper.updateTestSectionLogFromDTo(testSectionLogiqueDTo, existingTestSectionLog.get());
        }
        return null;
    }

    @Override
    public void deleteTestSectionLogique(Long id) {
        try {
            testSectionLogiqueRepository.deleteById(id);
            System.out.println("test_section_Logique deleted successfully with ID:" + id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("test_Section_Logique with  Id" + id + "not found");
            throw e;
        } catch (Exception e) {
            System.err.println("error deleting test_section_Logique");
        }
    }


    @Override
    public void deleteAllTestSectionLogique() {
        try {
            testSectionLogiqueRepository.deleteAll();
            System.out.println("All tests_section deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all tests Section");
            throw e;
        }
    }

    @Override
    public Question_Logique_DTo createPrivateQuestionLogique(Question_Logique_DTo questionLogiqueDTo, Long testSectionId, MultipartFile imageFile) throws IOException {
        Test_Section_Logique testSectionLogique = testSectionLogiqueRepository.findById(testSectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section de test logique  non trouvée avec l'ID : " + testSectionId));

        questionLogiqueDTo.setIsPrivate(true);

        Question_Logique_DTo createdQuestionLogique = questionLogiqueService.createQuestionLogique(questionLogiqueDTo, imageFile);

        Question_Logique questionLogiqueEntity = questionLogiqueRepository.findById(createdQuestionLogique.getId())
                .orElseThrow(() -> new IllegalArgumentException("Question logique non trouvée avec l'ID : " + createdQuestionLogique.getId()));

        List<Question> questions = testSectionLogique.getQuestions();
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(questionLogiqueEntity);
        testSectionLogique.setQuestions(questions);
        testSectionLogique.setCreatedAt(new Date());
        testSectionLogiqueRepository.save(testSectionLogique);
        return createdQuestionLogique;
    }

@Override
    public List<Question_Logique> getPrivateQuestionsLogiqueByTestSectionIdAll(Long testSectionId) {
        Test_Section_Logique testSection = testSectionLogiqueRepository.findById(testSectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section de test non trouvée avec l'ID : " + testSectionId));

        List<Question> allQuestions = testSection.getQuestions();

        List<Question_Logique> privateQuestions = allQuestions.stream()
                .filter(question -> question instanceof Question_Logique && question.getIsPrivate())
                .map(question -> (Question_Logique) question)
                .collect(Collectors.toList());

        return privateQuestions;
    }
    @Override
    public Test_Section_Logique getTestSectionByUUID(UUID testSectionUUID) {
        return testSectionLogiqueRepository.findByUuid(testSectionUUID);
    }
    @Override
    public long countTestSectionByUserUUID(UUID userUUID) {
        List<TestSection_LogiqueDTo> testSections = testSectionLogiqueRepository.findByUserUUID(userUUID)
                .stream()
                .map(testSectionLogiqueMapper::TEST_SECTION_LOGIQUE_D_TO)
                .collect(Collectors.toList());
        return testSections.size();
    }
    @Override
    public int sommeQuestionsPriveesLogParUtilisateur(UUID userUUID) {
        List<Test_Section_Logique> testSectionTechList = testSectionLogiqueRepository.findByUserUUID(userUUID);

        int sommeQuestionsPrivees = 0;

        for (Test_Section_Logique testSectionLogique : testSectionTechList) {

            List<Question> questions = testSectionLogique.getQuestions();
            int nombreQuestionsDansSection = questions != null ? questions.size() : 0;


            sommeQuestionsPrivees += nombreQuestionsDansSection;
        }

        return sommeQuestionsPrivees;
    }
    @Override
    public List<Test_Section_Logique> findByExperienceAndDifficulty(String experience, Difficulty difficulty) {
        return testSectionLogiqueRepository.findByExperienceAndDifficulty(experience, difficulty);
    }
}