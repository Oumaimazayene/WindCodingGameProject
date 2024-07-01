package com.demo.demo.Controller;

import com.demo.demo.Service.QuestionService;
import com.demo.demo.dtos.QuestionDTo;
import com.demo.demo.entity.Question;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/questions")
public class QuestionController {
    private final QuestionService questionService ;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }


    @GetMapping("/questions")
    public List<QuestionDTo> getAllQuestions() {
        return questionService.getAllQuestions();
    }


    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }

    @DeleteMapping("/questions")
    public void deleteAllQuestions() {
        questionService.deleteAllQuestions();
    }
}
