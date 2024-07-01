package com.demo.demo.Controller;

import com.demo.demo.Service.TestSectionService;
import com.demo.demo.dtos.*;
import com.demo.demo.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v2/test-Sections")
public class TestSectionController {
    private final TestSectionService testSectionService;

    public TestSectionController(TestSectionService testSectionService) {
        this.testSectionService = testSectionService;
    }
    @GetMapping("/all")
    public List<TestSectionDto> getAllTestSection() {
        return testSectionService.getAllTestSection();
    }
    @DeleteMapping("/deleteAll")
    public void deleteAllTestSection() {
        testSectionService.deleteAllTestSection();
    }
    @GetMapping("/{testSectionId}/private-questions")
    public ResponseEntity<?> getPrivateQuestionsByTestSectionIdAll(@PathVariable Long testSectionId) {
        try {
            return ResponseEntity.ok(testSectionService.getPrivateQuestionsByTestSectionIdAll(testSectionId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/{testSectionId}/private-questions/random/{difficulty}/{size}")
    public ResponseEntity<List<Question_Logique>> getRandomPrivateQuestions(
            @PathVariable("testSectionId") Long testSectionId,
            @PathVariable("difficulty") String difficulty,
            @PathVariable("size") int size) {
        try {
            List<Question_Logique> privateQuestions = testSectionService.getPrivateQuestionsByTestSectionId(testSectionId, difficulty, size);
            return new ResponseEntity<>(privateQuestions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
