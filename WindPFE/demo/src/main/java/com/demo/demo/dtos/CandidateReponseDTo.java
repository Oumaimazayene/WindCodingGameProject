package com.demo.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CandidateReponseDTo {
    @JsonProperty("idQuestion")
    private Long idQuestion;

    @JsonProperty("reponses")
    private List<String> reponses;

}
