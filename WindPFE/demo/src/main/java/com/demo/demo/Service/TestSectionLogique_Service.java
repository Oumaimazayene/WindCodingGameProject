package com.demo.demo.Service;

import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.dtos.TestSectionDto;
import com.demo.demo.dtos.TestSection_LogiqueDTo;
import com.demo.demo.entity.Difficulty;
import com.demo.demo.entity.Question_Logique;
import com.demo.demo.entity.Test_Section;
import com.demo.demo.entity.Test_Section_Logique;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TestSectionLogique_Service {

    Test_Section_Logique getTestSectionById(Long id);
    List<TestSection_LogiqueDTo> getAllTestSection();
    TestSection_LogiqueDTo createTestSection(TestSection_LogiqueDTo testSectionLogiqueDTo, UUID userUUId);
    TestSection_LogiqueDTo updateTestSection(Long id,  TestSection_LogiqueDTo testSectionLogiqueDTo);
    void deleteTestSectionLogique(Long id);
    void  deleteAllTestSectionLogique();
    List<TestSection_LogiqueDTo> getTestSectionByUserUUID(UUID userUUID);
    List<Question_Logique> getPrivateQuestionsLogiqueByTestSectionIdAll(Long testSectionId);
    Test_Section_Logique getTestSectionByUUID(UUID testSectionUUID);
    long countTestSectionByUserUUID(UUID userUUID);
    int sommeQuestionsPriveesLogParUtilisateur(UUID userUUID);
    List<Test_Section_Logique>findByExperienceAndDifficulty (String experience, Difficulty difficulty);
    Question_Logique_DTo createPrivateQuestionLogique(Question_Logique_DTo questionLogiqueDTo, Long testSectionId, MultipartFile imageFile)throws IOException;
}
