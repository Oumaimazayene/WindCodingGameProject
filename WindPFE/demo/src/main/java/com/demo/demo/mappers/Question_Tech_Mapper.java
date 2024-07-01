package com.demo.demo.mappers;

import com.demo.demo.dtos.QuestionDTo;
import com.demo.demo.dtos.Question_Tech_DTo;
import com.demo.demo.entity.Question_Tech;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface Question_Tech_Mapper {
    Question_Tech ToQuestionTech(Question_Tech_DTo questionTechDTo);
    Question_Tech_DTo ToQuestionTechDTo(Question_Tech questionTech);

    void updateQuestionTechFromDTO(Question_Tech_DTo questionTechDTo,@MappingTarget Question_Tech existingQuestionTech);
}

