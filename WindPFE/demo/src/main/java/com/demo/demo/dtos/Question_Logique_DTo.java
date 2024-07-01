package com.demo.demo.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Question_Logique_DTo extends  QuestionDTo {
    private  Long id;
    private String URLimage;

}
