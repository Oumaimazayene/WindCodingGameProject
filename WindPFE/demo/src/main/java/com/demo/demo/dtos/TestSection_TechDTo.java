package com.demo.demo.dtos;

import com.demo.demo.entity.Difficulty;
import jakarta.persistence.*;

import lombok.Data;

import java.util.List;
@Data
public class TestSection_TechDTo extends TestSectionDto{
    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<Difficulty> difficulties;
    @ElementCollection
    List<Integer> publicNumber;
    @ElementCollection
    List<Integer> privateNumber;
    List<Long> domain_id;
}
