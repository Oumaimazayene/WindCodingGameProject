package com.demo.demo.Service;

import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.entity.Difficulty;
import com.demo.demo.entity.Question_Logique;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface Question_Logique_Service {
    Question_Logique getQuestionLogiqueById(Long id);
     List<Question_Logique> getAllQuestionLogique();
    Question_Logique_DTo createQuestionLogique(Question_Logique_DTo questionLogiqueDto,MultipartFile imageFile)throws IOException;
    Question_Logique_DTo updateQuestionLogique(Long id, Question_Logique_DTo questionLogiqueDTo);
    void deleteQuestionLogique(Long id);
    void  deleteAllQuestionLogique();
    List<Question_Logique> getQuestionLogiqueByDifficultyAndIsNotPrivate(String difficulty, int size);
    List<Question_Logique> findTypeNameByTypeAndDifficulty(String type, Difficulty difficulty);
    long countLogiqueQuestions();
}
