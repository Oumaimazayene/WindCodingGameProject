package com.demo.demo.Controller;
import com.demo.demo.Service.Question_Tech_Service;
import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.entity.Difficulty;
import com.demo.demo.entity.Question_Logique;
import com.demo.demo.entity.Question_Tech;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/questionsTech")
public class QuestionTechController {
    private final Question_Tech_Service questionTechService ;

    public QuestionTechController(Question_Tech_Service questionTechService) {
        this.questionTechService = questionTechService;
    }

    @GetMapping("/{id}")
    public Question_Tech getQuestion_techById(@PathVariable Long id) {
        return questionTechService.getQuestion_techById(id);
    }



    @GetMapping("/all")
    public List<Question_Tech> getAllQuestionTech() {
        return questionTechService.getAllQuestionTech();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createQuestionTech(@RequestBody Question_Tech_DTo questionTechDTo) {
        try {
            Question_Tech_DTo createdQuestion = questionTechService.createQuestionTech(questionTechDTo);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to create question: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")

    public Question_Tech_DTo updateQuestionTech(@PathVariable Long id, @RequestBody Question_Tech_DTo questionTechDTo) {
        return questionTechService.updateQuestionTech(id,questionTechDTo);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestionTech(@PathVariable Long id) {
        questionTechService.deleteQuestionTech(id);
    }

    @DeleteMapping("/questiontechniques")
    public void deleteAllQuestionTech() {
        questionTechService.deleteAllQuestionTech();
    }

    @GetMapping("/questions/technique/{difficulty}/{domain}")
    public List<Question_Tech> getQuestionTechniqueByDifficultyAndIsNotPrivate(
            @PathVariable Difficulty difficulty,
            @PathVariable String domain,
            @RequestParam Integer size
    ) {
        return questionTechService.getQuestionTechniqueByDifficultyAndIsNotPrivate(difficulty,domain, size);
    }
    @GetMapping("/technical/filter")
    public ResponseEntity <List<Question_Tech>> filterTechnicalQuestionsByTypeAndDifficultyAndDomainName(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) String domainName) {

        List<Question_Tech> questionsFilter = questionTechService.filterTechnicalQuestionsByTypeAndDifficultyAndDomainName(type, difficulty, domainName);
        return ResponseEntity.ok(questionsFilter);

    }
    @GetMapping("/technical/count")
    public long countTechnicalQuestions() {
        return questionTechService.countTechnicalQuestions();
    }
}

