package com.demo.demo.dtos;


import com.demo.demo.entity.Difficulty;
import lombok.Data;
import org.apache.commons.lang3.builder.Diff;

import java.util.List;


@Data
public class QuestionDTo {

    private String title;
    private String questionBody;
    private Integer score;
    private Difficulty difficulty;
    private Integer time;
    private Boolean isPrivate;
    private  Long type_id;
    private List<AnswerDTo> answer;

}
