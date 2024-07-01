package com.demo.demo.mappers;

import com.demo.demo.dtos.AnswerDTo;
import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Answer;
import com.demo.demo.entity.Candidate;
import com.demo.demo.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerDTo ToAnswerDTO(Answer answer) ;
    Answer ToAnswer(AnswerDTo answerDTo);

    void updateAnswerFromDTO(AnswerDTo answerDTo, @MappingTarget  Answer existingAnswer );
}
