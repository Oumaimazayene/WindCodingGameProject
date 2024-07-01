package com.demo.demo.dtos;


import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class TestDTo {
    public Long id;
    public Date createdAt;
    public Boolean isSubmitted;
    public  Integer ScoreTotale;
    public Integer correctAnswerCount ;
    public Double SuccessRate;
    public Integer scorefinale;
    public  Date SubmittedDate;
    private List<QuestionDTo> questionss;
    private UUID testSectionUUID;
    public Integer timeTotale;




}



