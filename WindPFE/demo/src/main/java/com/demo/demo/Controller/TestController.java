package com.demo.demo.Controller;

import com.demo.demo.Repository.TestSectionRepository;
import com.demo.demo.Service.TestService;
import com.demo.demo.dtos.CandidateReponseDTo;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.dtos.TestDTo;
import com.demo.demo.entity.*;
import io.micrometer.common.util.internal.logging.InternalLogger;
import jakarta.xml.bind.SchemaOutputResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v2/tests")
public class TestController {
    private  final TestService testService ;
    private  final TestSectionRepository testSectionRepository;
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    public TestController(TestService testService, TestSectionRepository testSectionRepository) {
        this.testService = testService;
        this.testSectionRepository = testSectionRepository;
    }
    @GetMapping("/{id}")
    public Test getTestById(@PathVariable Long id) {
        return testService.getTestById(id);
    }

    @GetMapping("/tests")
    public List<TestDTo> getAllTests() {
        return testService.getAllTests();
    }


    @PutMapping("/{id}")
    public TestDTo updateType(@PathVariable Long id, @RequestBody TestDTo testDTo) {
        return testService.updateTest(id, testDTo);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
    }

    @DeleteMapping("/test")
    public void deleteAllTests() {
      testService.deleteAllTests();
    }
    @PostMapping("/createTestLogic")
    public ResponseEntity<?> createTest(@RequestParam UUID testSectionUUID,
                                        @RequestParam Integer size,
                                        @RequestParam Integer privateqts) {
        try {
            Test test = testService.createLogicalTestLogic(testSectionUUID, size, privateqts);
            System.out.println("this.Test"+ test);
            return new ResponseEntity<>(test, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println("erreur" + e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/generate-rapport/{testId}")
    public Test generateTestReport(@PathVariable Long testId, @RequestBody List<CandidateReponseDTo> candidateReponseDtos) {
        return testService.genererTestRapport(testId, candidateReponseDtos);
    }

    @PostMapping("/createTestTechnique")
    public ResponseEntity<Object> createTestTechnique(@RequestParam UUID testSectionTechId) {
        try {
            Test test = testService.createTestTechnique(testSectionTechId);
            String message = "Test technique créé avec succès avec l'ID: " + test.getId();
            return ResponseEntity.ok().body(Collections.singletonMap("message", message));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/tests/{testSectionUUID}")
    public List<Test> getTestsByTestSectionUUID(@PathVariable UUID testSectionUUID) {
        return testService.getTestsByTestSectionUUID(testSectionUUID);
    }

    @PostMapping("/generate-freemarker-report/{testId}")
    public ResponseEntity<?> generateFreemarkerTestReport(@PathVariable Long testId, @RequestBody List<CandidateReponseDTo> candidateReponseDtos) {
        try {
            Test test = testService.getTestById(testId);
            if (test == null) {
                return ResponseEntity.notFound().build();
            }
            List<Question> questions = test.getQuestions();
            testService.generateFreemarkerTestReport(test, candidateReponseDtos);
            return ResponseEntity.ok("Rapport Freemarker généré avec succès pour le test avec l'ID: " + testId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la génération du rapport Freemarker : " + e.getMessage());
        }
    }
    @PutMapping("/submitted/{id}")
    public ResponseEntity<Test> submitTest(@PathVariable Long id) {
        try {
            Test submittedTest = testService.submitTest(id);
            return ResponseEntity.ok(submittedTest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
