package com.demo.demo.dtos;


import com.demo.demo.entity.Difficulty;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TestSectionDto {
 private Long id;
 private String testsectionName;
 private String experience;
 private Difficulty difficulty;
 private Integer qtsNumber ;
 public UUID userUUID;
 public UUID uuid ;
 public Date createdAt;

}
