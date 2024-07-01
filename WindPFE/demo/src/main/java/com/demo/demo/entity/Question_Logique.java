package com.demo.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
@Data
 @Entity
@Table(name = "question_logique")
@DiscriminatorValue("Logique")
public class Question_Logique extends  Question {

    @Column(name = "URLimage")
    private String URLimage;

}
