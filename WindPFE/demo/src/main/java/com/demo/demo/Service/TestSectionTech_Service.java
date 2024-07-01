package com.demo.demo.Service;

import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.dtos.TestSection_TechDTo;
import com.demo.demo.entity.Difficulty;
import com.demo.demo.entity.Question;
import com.demo.demo.entity.Question_Tech;
import com.demo.demo.entity.Test_Section_Tech;

import java.util.List;
import java.util.UUID;

public interface TestSectionTech_Service {

    Test_Section_Tech getTestSectionById(Long id);
    List<TestSection_TechDTo> getAllTestSection();
    TestSection_TechDTo  createTestSectionTech(TestSection_TechDTo testSectionTechDTo , UUID user_uuid);
    TestSection_TechDTo updateTestSection(Long id,  TestSection_TechDTo testSectionTechDTo);
    void deleteTestSectionTech(Long id);
    void  deleteAllTestSectionTech();
    List<Question_Tech> getPrivateQuestionsTechByTestSectionIdAll(Long testSectionId);
    List<Test_Section_Tech> getTestSectionByUserUUID(UUID userUUID);
    Question_Tech_DTo createPrivateQuestionTechnique(Question_Tech_DTo questionTechDTo , Long testSectionId);
    List<Question_Tech> getRandomTechnicalQuestionsByTestSectionId(Long testSectionId, Difficulty difficulty, String domain, int numberOfQuestions);
    int sommeQuestionsPriveesParUtilisateur(UUID userUUID);
    List<Test_Section_Tech>findByExperienceAndDifficulty (String experience, Difficulty difficulty);
    List<Question_Tech> getPrivateQuestionsByUserUUID(UUID userUUID);
    void assignPrivateQuestionsToAnotherTestSection(List<Long> questionIds, Long targetTestSectionId);
}
