package com.demo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "test_section_Logique")
@DiscriminatorValue("Logique")

public class Test_Section_Logique extends Test_Section { }
