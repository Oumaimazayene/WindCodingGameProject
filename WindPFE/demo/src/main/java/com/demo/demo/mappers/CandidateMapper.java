package com.demo.demo.mappers;

import com.demo.demo.dtos.CandidateDTO;
import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Candidate;
import com.demo.demo.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    Candidate ToCandidate(CandidateDTO candidateDTO);
    CandidateDTO ToCandidateDTO(Candidate candidate);

    void updateCandidateFromDTO(CandidateDTO candidateDTO, @MappingTarget Candidate existingCandidate);
}
