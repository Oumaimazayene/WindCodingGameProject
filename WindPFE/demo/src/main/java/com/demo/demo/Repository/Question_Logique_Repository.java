package com.demo.demo.Repository;

import com.demo.demo.entity.Difficulty;
import com.demo.demo.entity.Question;
import com.demo.demo.entity.Question_Logique;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Question_Logique_Repository  extends JpaRepository<Question_Logique,Long> {


    List<Question_Logique> findByDifficultyAndIsPrivateFalse(Difficulty difficulty);


    @Query("SELECT q FROM Question_Logique q WHERE " +
            "(:type IS NULL OR q.type.name LIKE CONCAT('%', :type, '%')) " +
            "AND (:difficulty IS NULL OR q.difficulty = :difficulty)")
    List<Question_Logique> filterLogicalQuestionsByTypeAndDifficulty(
            @Param("type") String type,
            @Param("difficulty") Difficulty difficulty);


}





