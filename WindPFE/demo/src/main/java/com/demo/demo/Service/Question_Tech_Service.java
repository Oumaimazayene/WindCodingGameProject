package com.demo.demo.Service;

import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.entity.Difficulty;
import com.demo.demo.entity.Domaine;
import com.demo.demo.entity.Question_Logique;
import com.demo.demo.entity.Question_Tech;

import java.util.List;

public interface Question_Tech_Service {

    Question_Tech getQuestion_techById(Long id);
    List<Question_Tech> getAllQuestionTech();
    Question_Tech_DTo createQuestionTech(Question_Tech_DTo questionTechDTo);
    Question_Tech_DTo updateQuestionTech(Long id, Question_Tech_DTo questionTechDTo);
    void deleteQuestionTech(Long id);
    void  deleteAllQuestionTech();
    List<Question_Tech> filterTechnicalQuestionsByTypeAndDifficultyAndDomainName(String type, Difficulty difficulty, String domainName );
    List<Question_Tech> getQuestionTechniqueByDifficultyAndIsNotPrivate(Difficulty difficulty, String domain, int size);
    long countTechnicalQuestions();
}
