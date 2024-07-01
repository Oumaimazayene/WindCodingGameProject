package com.demo.demo.Repository;

import com.demo.demo.entity.Question;
import com.demo.demo.entity.Question_Logique;
import com.demo.demo.entity.Test_Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

}

