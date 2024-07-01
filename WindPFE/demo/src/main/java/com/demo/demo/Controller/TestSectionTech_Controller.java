package com.demo.demo.Controller;

import com.demo.demo.Service.TestSectionTech_Service;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.dtos.TestSectionDto;
import com.demo.demo.dtos.TestSection_TechDTo;
import com.demo.demo.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v2/test-Sections-Tech")
public class TestSectionTech_Controller {

    private  final TestSectionTech_Service testSectionTechService;
    @GetMapping("/{id}")
    public Test_Section getTestSectionById(@PathVariable Long id) {
        return testSectionTechService.getTestSectionById(id);
    }

    @GetMapping("/all")
    public List<TestSection_TechDTo> getAllTestSection() {
        return testSectionTechService.getAllTestSection();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createTestSection(@RequestBody TestSection_TechDTo testSectionTechDTo, @RequestParam UUID user_uuid) {
        return ResponseEntity.ok().body(testSectionTechService.createTestSectionTech(testSectionTechDTo, user_uuid));
    }

    @GetMapping("/user/{userUUID}")
    public List<Test_Section_Tech> getTestSectionByUserUUID(@PathVariable UUID userUUID) {
        return testSectionTechService.getTestSectionByUserUUID(userUUID);

    }
    @PutMapping("/{id}")

    public TestSection_TechDTo updateTestSection(@PathVariable Long id, @RequestBody TestSection_TechDTo testSectionTechDTo) {
        return testSectionTechService.updateTestSection(id, testSectionTechDTo);
    }
    @DeleteMapping("/{id}")
    public void deleteTestSection(@PathVariable Long id) {
        testSectionTechService.deleteTestSectionTech(id);
    }
    @DeleteMapping("/deleteAll")
    public void deleteAllTestSection() {
        testSectionTechService.deleteAllTestSectionTech();
    }
    @PostMapping("/createPrivateQuestionTechnique")
    public ResponseEntity<?> createPrivateQuestionTechnique(@RequestBody Question_Tech_DTo questionTechDTo,
                                                            @RequestParam Long testSectionId) {
        try {
            Question_Tech_DTo createdQuestion = testSectionTechService.createPrivateQuestionTechnique(questionTechDTo, testSectionId);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to create question: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/test-sections/{testSectionId}/technical-questions/random")
    public List<Question_Tech> getRandomTechnicalQuestionsByTestSectionId(
            @PathVariable Long testSectionId,
            @RequestParam Difficulty difficulty,
            @RequestParam String domain,
            @RequestParam int numberOfQuestions) {
        return testSectionTechService.getRandomTechnicalQuestionsByTestSectionId(testSectionId, difficulty, domain, numberOfQuestions);
    }
    @GetMapping("/test-section-Tech/{testSectionId}/private-questions")
    public List<Question_Tech> getPrivateQuestionsTechByTestSectionIdAll(@PathVariable Long testSectionId) {
        return testSectionTechService.getPrivateQuestionsTechByTestSectionIdAll(testSectionId);
    }
    @GetMapping("/private-questions-sum/{userUUID}")
    public ResponseEntity<Integer> getPrivateQuestionsSumByUserUUID(@PathVariable UUID userUUID) {
        int sommeQuestionsPrivees = testSectionTechService.sommeQuestionsPriveesParUtilisateur(userUUID);
        return new ResponseEntity<>(sommeQuestionsPrivees, HttpStatus.OK);
    }
    @GetMapping("/testSectionsTech/filter")
    public List<Test_Section_Tech> findByExperienceAndDifficulty(
            @RequestParam(value = "experience", required = false) String experience,
            @RequestParam(value = "difficulty", required = false) Difficulty difficulty) {
        return testSectionTechService.findByExperienceAndDifficulty(experience, difficulty);
    }
    @GetMapping("/private-questions/{userUUID}")
    public ResponseEntity<List<Question_Tech>> getPrivateQuestionsByUserUUID(@PathVariable UUID userUUID) {
        List<Question_Tech> privateQuestions = testSectionTechService.getPrivateQuestionsByUserUUID(userUUID);
        return ResponseEntity.ok(privateQuestions);
    }
    @PostMapping("/{targetTestSectionId}/assign-private-questions")
    public ResponseEntity<Void> assignPrivateQuestionsToAnotherTestSection(
            @PathVariable Long targetTestSectionId,
            @RequestBody List<Long> questionIds) {
        testSectionTechService.assignPrivateQuestionsToAnotherTestSection(questionIds, targetTestSectionId);
        return ResponseEntity.ok().build();
    }
}
