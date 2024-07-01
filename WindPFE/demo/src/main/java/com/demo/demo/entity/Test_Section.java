package com.demo.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // Ajout de l'import pour @NoArgsConstructor

import java.util.*;

@Data
@Entity
@Table(name = "test_section")
@AllArgsConstructor
@NoArgsConstructor // Annotation ajoutée pour générer un constructeur par défaut
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "test_section_type", discriminatorType = DiscriminatorType.STRING)
public class Test_Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "testsectionName")
    public String testsectionName;
    @Column(name = "experience")
    public String experience ;
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;
    public Integer qtsNumber ;
    @Column(name = "createdAt")
    public Date createdAt ;
    @Column(name = "useruuid")
    public UUID userUUID;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "testSection_question",
            joinColumns = @JoinColumn(name = "testSection_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    public List<Question> questions = new ArrayList<>();

    @Column
    private UUID uuid;
    @PrePersist
    private void prePersist() {
        this.uuid  = UUID.randomUUID();
    }
}
