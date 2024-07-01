package com.demo.demo.ServiceImpl;

import com.demo.demo.Repository.AnswerRepository;
import com.demo.demo.Repository.QuestionRepository;
import com.demo.demo.Service.AnswerService;
import com.demo.demo.dtos.AnswerDTo;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.entity.Answer;
import com.demo.demo.entity.Candidate;
import com.demo.demo.entity.Question;
import com.demo.demo.entity.Question_Tech;
import com.demo.demo.mappers.AnswerMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {
    private  final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final QuestionRepository questionRepository;
    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerMapper answerMapper, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public Answer getAnswerById(Long id) {

            return answerRepository.findById(id).orElse(null);
        }

    @Override
    public List<AnswerDTo> getAllAnswers() {
        return answerRepository.findAll().stream()
                .map(answerMapper::ToAnswerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AnswerDTo updateAnswer(Long id, AnswerDTo answerDTo) {
        Optional<Answer> existingAnswer = answerRepository.findById(id);
        if (existingAnswer.isPresent()) {
            answerMapper.updateAnswerFromDTO(answerDTo, existingAnswer.get());
            return answerMapper.ToAnswerDTO(answerRepository.save(existingAnswer.get()));
        }
        return null;
    }

    @Override
    public void deleteAnswer(Long id) {
        try {
            answerRepository.deleteById(id);
            System.out.println("Answer deleted successfully with ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Answer with ID " + id + " not found");
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting Answer");
            throw e;
        }
    }

    @Override
    public void deleteAllAnswers() {
        try {
            answerRepository.deleteAll();
            System.out.println("All Answers deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all Answers");
            throw e;
        }
    }
}
