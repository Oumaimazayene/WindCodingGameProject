package com.demo.demo.Repository;

import com.demo.demo.entity.Difficulty;
import com.demo.demo.entity.Test_Section_Logique;
import com.demo.demo.entity.Test_Section_Tech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TestSection_LogiqueRepository extends JpaRepository<Test_Section_Logique,Long> {

    List<Test_Section_Logique> findByUserUUID(UUID userUUID);

    Test_Section_Logique findByUuid(UUID uuid);

    @Query("SELECT ts FROM Test_Section_Logique ts WHERE " +
            "(:experience IS NULL OR CAST(ts.experience AS string ) LIKE CONCAT('%', CAST(:experience AS string ), '%')) " +
            "AND (:difficulty IS NULL OR ts.difficulty = :difficulty)")
    List<Test_Section_Logique> findByExperienceAndDifficulty(@Param("experience") String experience, @Param("difficulty") Difficulty difficulty);


}