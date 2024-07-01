package com.demo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "question_Tech")
@DiscriminatorValue("Technique")

public class Question_Tech extends  Question{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domaine_id" )
    private Domaine domain;

}
