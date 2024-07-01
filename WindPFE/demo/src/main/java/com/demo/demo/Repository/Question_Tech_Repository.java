package com.demo.demo.Repository;

import com.demo.demo.entity.Difficulty;
import com.demo.demo.entity.Question;
import com.demo.demo.entity.Question_Tech;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Question_Tech_Repository extends JpaRepository<Question_Tech, Long> {

    List<Question_Tech> findByDifficultyAndIsPrivateFalse(Difficulty difficulty);

    List<Question_Tech> findByDomainId(Long domaineId);

    @Query("SELECT q FROM Question_Tech q " +
            "WHERE (:type IS NULL OR q.type.name LIKE CONCAT('%', :type, '%')) " +
            "AND (:difficulty IS NULL OR q.difficulty = :difficulty) " +
            "AND (:domainName IS NULL OR q.domain.name LIKE CONCAT('%', :domainName, '%'))")
    List<Question_Tech> filterTechnicalQuestionsByTypeAndDifficultyAndDomainName(
            @Param("type") String type,
            @Param("difficulty") Difficulty difficulty,
            @Param("domainName") String domainName);

}
