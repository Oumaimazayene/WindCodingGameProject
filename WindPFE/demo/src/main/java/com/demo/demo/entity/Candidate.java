package com.demo.demo.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Table(name = "Candidate")
@Entity
public class Candidate {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long id;
        @Column(name = "firstName")
        public String firstName ;
    @Column(name = "lastName")

    public String lastName ;
    @Column(name = "email")

    public String email ;
}
