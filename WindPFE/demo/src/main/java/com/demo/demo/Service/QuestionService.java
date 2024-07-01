package com.demo.demo.Service;

import com.demo.demo.dtos.QuestionDTo;
import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.entity.Question;

import java.util.List;

public interface QuestionService {

    Question getQuestionById(Long id);
    List<QuestionDTo> getAllQuestions();
    void deleteQuestion(Long id);
    void  deleteAllQuestions();
}
