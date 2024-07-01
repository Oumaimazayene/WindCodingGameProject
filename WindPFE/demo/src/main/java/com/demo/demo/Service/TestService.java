package com.demo.demo.Service;

import com.demo.demo.dtos.CandidateReponseDTo;
import com.demo.demo.dtos.TestDTo;
import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Candidate;
import com.demo.demo.entity.Question;
import com.demo.demo.entity.Test;
import com.demo.demo.entity.Type;

import java.util.List;
import java.util.UUID;

public interface TestService {
    Test getTestById(Long id);
    List<TestDTo> getAllTests();
    TestDTo updateTest(Long id, TestDTo testDTo);
    void deleteTest(Long id);
    void  deleteAllTests();
    Test createLogicalTestLogic(UUID testSectionUUID, int size, int privateqts) ;
    Test createTestTechnique(UUID testSectionTech);
    List<Test> getTestsByTestSectionUUID(UUID testSectionUUID);
    Test genererTestRapport(Long testId, List<CandidateReponseDTo> candidateReponseDTos);
     void generateFreemarkerTestReport(Test test, List<CandidateReponseDTo> candidateReponseDTos);
    Test submitTest(Long id);
}
