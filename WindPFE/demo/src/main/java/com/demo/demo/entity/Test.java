package com.demo.demo.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JoinColumnOrFormula;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="Test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "createdAt")
    public Date createdAt ;
    @Column(name = "isSubmitted")
    public Boolean isSubmitted = false;
    @Column
    public  Integer ScoreTotale;
    @Column
    public Integer correctAnswerCount ;
    @Column
    public Double SuccessRate;
    @Column
    public Integer timeTotale;
    @Column
    public Integer scorefinale;
    @Column
    public  Date SubmittedDate;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Column
    private UUID testSectionUUID;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "test_question",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")) // Changer le nom de la colonne ici
    private List<Question> questions;

}
