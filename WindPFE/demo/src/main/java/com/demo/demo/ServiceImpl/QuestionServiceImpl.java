package com.demo.demo.ServiceImpl;

import com.demo.demo.Repository.QuestionRepository;
import com.demo.demo.Service.QuestionService;
import com.demo.demo.dtos.QuestionDTo;
import com.demo.demo.entity.Question;
import com.demo.demo.mappers.QuestionMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private  final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionMapper questionMapper, QuestionRepository questionRepository) {
        this.questionMapper = questionMapper;
        this.questionRepository = questionRepository;
    }


    @Override
    public Question getQuestionById(Long id) {

            return questionRepository.findById(id).orElse(null);
        }

    @Override
    public List<QuestionDTo> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(questionMapper::ToQuestionDTo)
                .collect(Collectors.toList());    }


    @Override
    public void deleteQuestion(Long id) {
        try {
            questionRepository.deleteById(id);
            System.out.println("question  deleted successfully with ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("question  with ID " + id + " not found");
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting question ");
            throw e;
        }
    }

    @Override
    public void deleteAllQuestions() {
        try {
            questionRepository.deleteAll();
            System.out.println("All Candidates deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all Candidates");
            throw e;
        }
    }
}
