package com.demo.demo.mappers;

import com.demo.demo.dtos.DomaineDTo;
import com.demo.demo.dtos.QuestionDTo;
import com.demo.demo.dtos.Question_Logique_DTo;
import com.demo.demo.entity.Domaine;
import com.demo.demo.entity.Question_Logique;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface Question_Logique_Mapper {
    Question_Logique ToQuestionLogique(Question_Logique_DTo questionLogiqueDTo);
    Question_Logique_DTo ToQuestionLogiqueDTo(Question_Logique questionLogique);
    void updateQuestionLogiqueFromDTO(Question_Logique_DTo questionLogiqueDTo, @MappingTarget Question_Logique existingQuestionLogique);

}
