package com.demo.demo.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "domaine")
public class Domaine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    @Column(name = "Lang")
    public String lang;
    @Column(name = "version")

    public String version ;
    @Column(name = "name", nullable = false)
    public String name ;



}
