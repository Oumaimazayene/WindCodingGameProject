package com.demo.demo.Service;

import com.demo.demo.dtos.AnswerDTo;
import com.demo.demo.dtos.CandidateDTO;
import com.demo.demo.entity.Answer;
import com.demo.demo.entity.Candidate;

import java.util.List;

public interface AnswerService {

    Answer getAnswerById(Long id);
    List<AnswerDTo> getAllAnswers();
    AnswerDTo updateAnswer(Long id, AnswerDTo answerDTo);
    void deleteAnswer(Long id);
    void  deleteAllAnswers();
}
