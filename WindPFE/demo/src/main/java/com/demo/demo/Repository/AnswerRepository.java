package com.demo.demo.Repository;

import com.demo.demo.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer ,Long> {
}
