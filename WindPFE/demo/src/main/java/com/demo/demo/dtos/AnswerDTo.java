package com.demo.demo.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class AnswerDTo {
    public Long id;
    private String answer;
    private Boolean isTrue;


}
