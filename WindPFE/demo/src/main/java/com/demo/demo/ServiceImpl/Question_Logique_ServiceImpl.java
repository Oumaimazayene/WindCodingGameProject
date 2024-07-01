package com.demo.demo.ServiceImpl;

import com.demo.demo.Repository.AnswerRepository;
import com.demo.demo.Repository.QuestionRepository;
import com.demo.demo.Repository.Question_Logique_Repository;
import com.demo.demo.Repository.TypeRepository;
import com.demo.demo.Service.ImageUploadService;
import com.demo.demo.Service.Question_Logique_Service;
import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.entity.*;
import com.demo.demo.mappers.AnswerMapper;
import com.demo.demo.mappers.Question_Logique_Mapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class Question_Logique_ServiceImpl implements Question_Logique_Service {
    private final Question_Logique_Mapper questionLogiqueMapper;
    private final Question_Logique_Repository questionLogiqueRepository;
    private final TypeRepository typeRepository;
    private final AnswerMapper answerMapper ;
private final ImageUploadService imageUploadService;
private  final QuestionRepository questionRepository;
private  final AnswerRepository answerRepository;
    @Override
    public Question_Logique getQuestionLogiqueById(Long id) {
        return questionLogiqueRepository.findById(id).orElse(null);
    }

    @Override
    public List<Question_Logique> getAllQuestionLogique() {
        List<Question_Logique> allQuestions = questionLogiqueRepository.findAll();
        List<Question_Logique> nonPrivateQuestions = new ArrayList<>();

        for (Question_Logique question : allQuestions) {
            if (!question.getIsPrivate()) {
                nonPrivateQuestions.add(question);
            }
        }

        return nonPrivateQuestions;
    }

    @Override
    public Question_Logique_DTo createQuestionLogique(Question_Logique_DTo questionLogiqueDto, MultipartFile imageFile) throws IOException {
        try {
            validateInputs(questionLogiqueDto, imageFile);


            Long typeId = questionLogiqueDto.getType_id();
            Type type = typeRepository.findById(typeId)
                    .orElseThrow(() -> new RuntimeException("Type non trouvé avec l'id " + typeId));


            Question_Logique questionLogique = questionLogiqueMapper.ToQuestionLogique(questionLogiqueDto);
            questionLogique.setType(type);

            List<Answer> answerList = questionLogiqueDto.getAnswer().stream()
                    .map(answerDto -> {
                        Answer answer = answerMapper.ToAnswer(answerDto);
                        answer.setQuestion(questionLogique);
                        return answer;
                    })
                    .collect(Collectors.toList());
            if (type.getName().equals("qcu")) {
                long countTrueAnswers = answerList.stream().filter(Answer::getIsTrue).count();
                if (countTrueAnswers != 1) {
                    throw new IllegalArgumentException("Pour les questions à choix unique, une seule réponse doit être vraie.");
                }
            }

            questionLogique.setAnswer(answerList);
            questionLogique.setURLimage(imageUploadService.saveImage(imageFile));

            Question_Logique savedQuestionLogique = questionLogiqueRepository.save(questionLogique);

            return questionLogiqueMapper.ToQuestionLogiqueDTo(savedQuestionLogique);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la création de la question logique : " + e.getMessage(), e);
        }
    }

    private void validateInputs (Question_Logique_DTo questionLogiqueDto, MultipartFile imageFile) {
        Objects.requireNonNull(questionLogiqueDto, "Question_Logique_DTo cannot be null");
        Objects.requireNonNull(imageFile, "Image file cannot be null");
    }




    @Override
public Question_Logique_DTo updateQuestionLogique(Long id, Question_Logique_DTo questionLogiqueDTo) {
    Optional<Question_Logique> existingQuestionLogique = questionLogiqueRepository.findById(id);
    if (existingQuestionLogique.isPresent()) {
        questionLogiqueMapper.updateQuestionLogiqueFromDTO(questionLogiqueDTo, existingQuestionLogique.get());
        return questionLogiqueMapper.ToQuestionLogiqueDTo(questionLogiqueRepository.save(existingQuestionLogique.get()));
    }
    return null;
}

    @Override
    public void deleteQuestionLogique(Long id) {
        try {
            Optional<Question_Logique> optionalQuestion = questionLogiqueRepository.findById(id);
            if (optionalQuestion.isPresent()) {
                Question_Logique question = optionalQuestion.get();
                List<Answer> answers = question.getAnswer();

                for (Answer answer : answers) {
                    answerRepository.deleteById(answer.getId());
                }
                questionLogiqueRepository.deleteById(id);
                questionRepository.deleteById(id);

                System.out.println("Question logique deleted successfully with ID: " + id);
            } else {
                System.out.println("Question logique with ID " + id + " not found");
            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Question logique with ID " + id + " not found");
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting question logique with ID: " + id);
            throw e;
        }
    }


@Override
public void deleteAllQuestionLogique() {
    try {
        questionLogiqueRepository.deleteAll();
        System.out.println("All question_logique deleted successfully");
    } catch (Exception e) {
        System.err.println("Error deleting all questions_logiques");
        throw e;
    }
}
    @Override
    public List<Question_Logique> getQuestionLogiqueByDifficultyAndIsNotPrivate(String difficultyStr, int size) {
        Difficulty difficulty;
        try {
            difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid difficulty level provided: " + difficultyStr);
        }

        List<Question_Logique> allQuestions = questionLogiqueRepository.findByDifficultyAndIsPrivateFalse(difficulty);
        List<Question_Logique> filteredQuestions = allQuestions.stream()
                .filter(question -> !question.getIsPrivate())
                .collect(Collectors.toList());

        if (filteredQuestions.isEmpty()) {
            return Collections.emptyList(); // Pas de questions disponibles
        }

        if (size >= filteredQuestions.size()) {
            return filteredQuestions;
        } else {
            List<Question_Logique> randomQuestions = new ArrayList<>();
            List<Integer> indexes = new ArrayList<>();
            Random random = new Random();

            for (int i = 0; i < filteredQuestions.size(); i++) {
                indexes.add(i);
            }
            Collections.shuffle(indexes);

            for (int i = 0; i < size; i++) {
                int randomIndex = random.nextInt(filteredQuestions.size());
                randomQuestions.add(filteredQuestions.get(indexes.get(randomIndex)));
            }

            return randomQuestions;
        }
    }


    @Override
    public List<Question_Logique> findTypeNameByTypeAndDifficulty(String type, Difficulty difficulty) {
        return questionLogiqueRepository.filterLogicalQuestionsByTypeAndDifficulty(type, difficulty);
    }
    @Override
    public long countLogiqueQuestions() {
        return questionLogiqueRepository.count();
    }


}
