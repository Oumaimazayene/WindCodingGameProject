package com.demo.demo.Controller;

import com.demo.demo.Service.TestSectionLogique_Service;
import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.dtos.TestSectionDto;
import com.demo.demo.dtos.TestSection_LogiqueDTo;
import com.demo.demo.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v2/test-Sections-logique")
public class TestSectionLogique_Controller {
    private  final TestSectionLogique_Service testSectionLogiqueService;
    @GetMapping("/{id}")
    public Test_Section getTestSectionById(@PathVariable Long id) {
        return testSectionLogiqueService.getTestSectionById(id);
    }

    @GetMapping("/all")
    public List<TestSection_LogiqueDTo> getAllTestSection() {
        return testSectionLogiqueService.getAllTestSection();
    }

    @GetMapping("/BYUser")
    public ResponseEntity<List<TestSection_LogiqueDTo>> getTestSectionsByUserUUID(@RequestParam UUID userUUID) {
        List<TestSection_LogiqueDTo> testSections = testSectionLogiqueService.getTestSectionByUserUUID(userUUID);
        return ResponseEntity.ok().body(testSections);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createTestSection(@RequestBody TestSection_LogiqueDTo testSectionLogiqueDTo , @RequestParam UUID user_uuid) {
        return ResponseEntity.ok().body(testSectionLogiqueService.createTestSection(testSectionLogiqueDTo,user_uuid));
    }

    @PutMapping("/{id}")

    public TestSectionDto updateTestSection(@PathVariable Long id, @RequestBody TestSection_LogiqueDTo testSectionLogiqueDTo) {
        return testSectionLogiqueService.updateTestSection(id, testSectionLogiqueDTo);
    }
    @DeleteMapping("/{id}")
    public void deleteTestSection(@PathVariable Long id) {
        testSectionLogiqueService.deleteTestSectionLogique(id);
    }
    @DeleteMapping("/deleteAll")
    public void deleteAllTestSection() {
        testSectionLogiqueService.deleteAllTestSectionLogique();
    }
    @PostMapping(value = "/logical_private_question", consumes = {"multipart/form-data"})
    public ResponseEntity<Question_Logique_DTo> createPrivateQuestionLogique(
            @RequestParam Long testSectionId,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam String questionLogiqueDtoJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Question_Logique_DTo questionLogiqueDTo = objectMapper.readValue(questionLogiqueDtoJson, Question_Logique_DTo.class);

            Question_Logique_DTo savedQuestionLogiqueDTo = testSectionLogiqueService.createPrivateQuestionLogique(questionLogiqueDTo, testSectionId, imageFile);
            if (savedQuestionLogiqueDTo != null) {
                return new ResponseEntity<>(savedQuestionLogiqueDTo, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/test-section/{testSectionId}/private-questions")
    public List<Question_Logique> getPrivateQuestionsByTestSectionId(@PathVariable Long testSectionId) {
        return testSectionLogiqueService.getPrivateQuestionsLogiqueByTestSectionIdAll(testSectionId);
    }
    @GetMapping("/testSectionuuid/{testSectionUUID}")
    public ResponseEntity<Test_Section_Logique> getTestSectionByUUID(@PathVariable UUID testSectionUUID) {
        Test_Section_Logique testSection = testSectionLogiqueService.getTestSectionByUUID(testSectionUUID);
        if (testSection != null) {
            return new ResponseEntity<>(testSection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/count/{userUUID}")
    public long countTestSectionsByUserUUID(@PathVariable UUID userUUID) {
        return testSectionLogiqueService.countTestSectionByUserUUID(userUUID);
    }
    @GetMapping("/private-questions-logic-sum/{userUUID}")
    public ResponseEntity<Integer> sommeQuestionsPriveesLogParUtilisateur(@PathVariable UUID userUUID) {
        int sommeQuestionsPrivees = testSectionLogiqueService.sommeQuestionsPriveesLogParUtilisateur(userUUID);
        return new ResponseEntity<>(sommeQuestionsPrivees, HttpStatus.OK);
    }
    @GetMapping("/testSections/filter")
    public List<Test_Section_Logique> findByExperienceAndDifficulty(
            @RequestParam(value = "experience", required = false) String experience,
            @RequestParam(value = "difficulty", required = false) Difficulty difficulty) {
        return testSectionLogiqueService.findByExperienceAndDifficulty(experience, difficulty);
    }

}
