package com.demo.demo.mappers;

import com.demo.demo.dtos.QuestionDTo;
import com.demo.demo.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question ToQuestion (QuestionDTo questionDTo);
    QuestionDTo ToQuestionDTo(Question question);
}
