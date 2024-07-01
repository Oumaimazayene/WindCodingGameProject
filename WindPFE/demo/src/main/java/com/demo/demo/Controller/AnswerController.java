package com.demo.demo.Controller;

import com.demo.demo.Service.AnswerService;
import com.demo.demo.dtos.AnswerDTo;

import com.demo.demo.entity.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/answers")
public class AnswerController {
    private  final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }
    @GetMapping("/{id}")
    public Answer getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id);
    }
    @GetMapping("/all")
    public List<AnswerDTo> getAllAnswers() {
        return answerService.getAllAnswers();
    }



    @PutMapping("/{id}")

    public AnswerDTo updateCandidate(@PathVariable Long id, @RequestBody AnswerDTo answerDTo) {
        return answerService.updateAnswer(id, answerDTo);
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable Long id) {
        answerService
                .deleteAnswer(id);
    }

    @DeleteMapping("/answers")
    public void deleteAllAnswers() {
        answerService.deleteAllAnswers();
    }

}
