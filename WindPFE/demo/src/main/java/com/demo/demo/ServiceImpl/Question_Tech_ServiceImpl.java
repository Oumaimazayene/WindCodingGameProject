package com.demo.demo.ServiceImpl;


import com.demo.demo.Repository.DomaineRepository;
import com.demo.demo.Repository.Question_Tech_Repository;
import com.demo.demo.Repository.TypeRepository;
import com.demo.demo.Service.Question_Tech_Service;
import com.demo.demo.Service.TypeService;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.entity.*;
import com.demo.demo.mappers.AnswerMapper;
import com.demo.demo.mappers.Question_Tech_Mapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class Question_Tech_ServiceImpl implements Question_Tech_Service {

    private  final Question_Tech_Repository questionTechRepository;
    private final Question_Tech_Mapper questionTechMapper;
    private  final DomaineRepository domaineRepository;
    private  final TypeRepository typeRepository ;
    private final AnswerMapper answerMapper ;
private final TypeService typeService;

    @Override
    public Question_Tech getQuestion_techById(Long id) {
        return  questionTechRepository.findById(id).orElse(null);
    }



    @Override

    public List<Question_Tech> getAllQuestionTech() {
        List<Question_Tech> allQuestions = questionTechRepository.findAll();
        List<Question_Tech> nonPrivateQuestions = new ArrayList<>();

        for (Question_Tech question : allQuestions) {
            if (!question.getIsPrivate()) {
                nonPrivateQuestions.add(question);
            }
        }

        return nonPrivateQuestions;
    }

@Override
    public Question_Tech_DTo createQuestionTech(Question_Tech_DTo questionTechDTo) {
        Long domainId = questionTechDTo.getDomain_id();
        Long typeId = questionTechDTo.getType_id();

        Domaine domaine = domaineRepository.findById(domainId)
                .orElseThrow(() -> new RuntimeException("Domaine non trouvé avec l'id " + domainId));

        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new RuntimeException("Type non trouvé avec l'id " + typeId));

        Question_Tech questionTech = questionTechMapper.ToQuestionTech(questionTechDTo);
        questionTech.setDomain(domaine);
        questionTech.setType(type);
    List<Answer> answerList = questionTechDTo.getAnswer().stream()
            .map(answerDto -> {
                Answer answer = answerMapper.ToAnswer(answerDto);

                answer.setQuestion(questionTech);

                return answer;
            })
            .collect(Collectors.toList());

    questionTech.setAnswer(answerList);


    Question_Tech savedQuestionTech = questionTechRepository.save(questionTech);

    Question_Tech_DTo responseDto = questionTechMapper.ToQuestionTechDTo(savedQuestionTech);

        return responseDto;
    }


    @Override
    public Question_Tech_DTo updateQuestionTech(Long id, Question_Tech_DTo questionTechDTo) {
        Optional<Question_Tech> optionalQuestionTech = questionTechRepository.findById(id);
        Question_Tech existingQuestionTech = optionalQuestionTech.orElseThrow(() -> new RuntimeException("Question technique n'existe pas avec l'ID:" + id));
        existingQuestionTech.setTitle(questionTechDTo.getTitle());
        existingQuestionTech.setQuestionBody(questionTechDTo.getQuestionBody());
        existingQuestionTech.setDifficulty(questionTechDTo.getDifficulty());
        existingQuestionTech.setScore(questionTechDTo.getScore());
        existingQuestionTech.setTime(questionTechDTo.getTime());
        Long typeId = questionTechDTo.getType_id();
        Type type = typeService.getTypeById(typeId);
        existingQuestionTech.setType(type);
        List<Answer> currentAnswers = existingQuestionTech.getAnswer();
        Question_Tech finalExistingQuestionTech = existingQuestionTech;
        List<Answer> updatedAnswers = questionTechDTo.getAnswer().stream()
                .map(answerDto -> {
                    Answer answer = answerMapper.ToAnswer(answerDto);
                    answer.setQuestion(finalExistingQuestionTech);
                    return answer;
                })
                .collect(Collectors.toList());

        for (Answer updatedAnswer : updatedAnswers) {
            for (Answer currentAnswer : currentAnswers) {
                if (Long.valueOf(updatedAnswer.getId()).equals(currentAnswer.getId())) {
                    // Mettez à jour les détails de la réponse existante
                    currentAnswer.setAnswer(updatedAnswer.getAnswer());

                    break;
                }
            }
        }
        for (Answer updatedAnswer : updatedAnswers) {
            if (!currentAnswers.contains(updatedAnswer)) {
                currentAnswers.add(updatedAnswer);
            }
        }
        currentAnswers.removeIf(currentAnswer -> !updatedAnswers.contains(currentAnswer));
        existingQuestionTech.setAnswer(currentAnswers);
        existingQuestionTech = questionTechRepository.save(existingQuestionTech);
        return questionTechMapper.ToQuestionTechDTo(existingQuestionTech);
    }

    @Override
    public void deleteQuestionTech(Long id) {
        try {
            questionTechRepository.deleteById(id);
            System.out.println("question Technique deleted successfully with ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("question Technique with ID " + id + " not found");
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting question Technique");
            throw e;
        }
    }

    @Override
    public void deleteAllQuestionTech() {
        try {
            questionTechRepository.deleteAll();
            System.out.println("All questions Technique deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all questions Techniques");
            throw e;
        }
    }
    @Override
    public List<Question_Tech> getQuestionTechniqueByDifficultyAndIsNotPrivate(Difficulty difficulty, String domain, int size) {
        Objects.requireNonNull(difficulty, "Difficulty cannot be null");
        Objects.requireNonNull(domain, "Domain cannot be null");
        Objects.requireNonNull(questionTechRepository, "Question repository cannot be null");


        List<Question_Tech> allQuestions = questionTechRepository.findByDifficultyAndIsPrivateFalse(difficulty);

        List<Question_Tech> filteredQuestions = allQuestions.stream()
                .filter(question -> !question.getIsPrivate() && domain.equals(question.getDomain().getName()))
                .toList();

        if (filteredQuestions.isEmpty() || filteredQuestions.size() < size) {
            return Collections.emptyList();
        }

        List<Question_Tech> randomQuestions = new ArrayList<>();
        Set<Integer> selectedIndexes = new HashSet<>();
        Random random = new Random();

        while (randomQuestions.size() < size && selectedIndexes.size() < filteredQuestions.size()) {
            int randomIndex = random.nextInt(filteredQuestions.size());
            if (!selectedIndexes.contains(randomIndex)) {
                randomQuestions.add(filteredQuestions.get(randomIndex));
                selectedIndexes.add(randomIndex);
            }
        }

        return randomQuestions;
    }
    @Override
    public List<Question_Tech> filterTechnicalQuestionsByTypeAndDifficultyAndDomainName(String type, Difficulty difficulty, String domainName ) {
        return questionTechRepository.filterTechnicalQuestionsByTypeAndDifficultyAndDomainName(type, difficulty, domainName);
    }
    @Override
    public long countTechnicalQuestions() {
        return questionTechRepository.count();
    }


}























