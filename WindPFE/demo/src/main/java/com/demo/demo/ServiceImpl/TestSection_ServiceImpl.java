package com.demo.demo.ServiceImpl;

import com.demo.demo.Repository.Question_Logique_Repository;
import com.demo.demo.Repository.Question_Tech_Repository;
import com.demo.demo.Repository.TestRepository;
import com.demo.demo.Repository.TestSectionRepository;
import com.demo.demo.Service.Question_Logique_Service;
import com.demo.demo.Service.Question_Tech_Service;
import com.demo.demo.Service.TestSectionService;
import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.dtos.*;
import com.demo.demo.entity.*;
import com.demo.demo.mappers.TestSectionMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TestSection_ServiceImpl implements TestSectionService {
    private final TestSectionRepository testSectionRepository;
    private final TestSectionMapper testSectionMapper;
    private final Question_Tech_Service questionTechService;
    private final Question_Tech_Repository questionTechRepository;
    private  final TestRepository testRepository;


    @Override
    public List<TestSectionDto> getAllTestSection() {
        return testSectionRepository.findAll().stream()
                .map(testSectionMapper::TEST_SECTION_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllTestSection() {
        try {
            testSectionRepository.deleteAll();
            System.out.println("All tests_section deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all tests Section");
            throw e;
        }
    }
    @Override
    public List<Question> getPrivateQuestionsByTestSectionIdAll(Long testSectionId) {
        Test_Section testSection = testSectionRepository.findById(testSectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section de test non trouvée avec l'ID : " + testSectionId));

        List<Question> allQuestions = testSection.getQuestions();
        List<Question> privateQuestions = new ArrayList<>();

        if (allQuestions != null) {
            for (Question question : allQuestions) {
                Question questions = (Question) question;
                if (questions.getIsPrivate()) {
                    privateQuestions.add(questions);
                }
            }
        }

        return privateQuestions;
    }
    @Override


    public List<Question_Logique> getPrivateQuestionsByTestSectionId(Long testSectionId, String difficulty, int size) {
        Test_Section testSection = testSectionRepository.findById(testSectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section de test non trouvée avec l'ID : " + testSectionId));

        List<Question> allQuestions = testSection.getQuestions();

        if (allQuestions == null) {
            return Collections.emptyList();
        }

        Set<Question_Logique> selectedQuestions = new HashSet<>();
        List<Question_Logique> privateQuestions = new ArrayList<>();
        for (Question question : allQuestions) {
            if (question instanceof Question_Logique && question.getIsPrivate() && question.getDifficulty().toString().equals(difficulty)) {
                Question_Logique logiqueQuestion = (Question_Logique) question;
                if (!selectedQuestions.contains(logiqueQuestion)) {
                    privateQuestions.add(logiqueQuestion);
                    selectedQuestions.add(logiqueQuestion);
                }
            }
        }

        Collections.shuffle(privateQuestions);

        return privateQuestions.size() > size ? privateQuestions.subList(0, size) : privateQuestions;
    }




}



