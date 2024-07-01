package com.demo.demo.dtos;


import lombok.Data;

@Data
public class Question_Tech_DTo extends  QuestionDTo{
    private  Long id;
    private Long domain_id;
}
