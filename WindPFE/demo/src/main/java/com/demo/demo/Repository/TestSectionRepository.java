package com.demo.demo.Repository;

import com.demo.demo.entity.Question_Logique;
import com.demo.demo.entity.Test_Section;
import com.demo.demo.entity.Test_Section_Logique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TestSectionRepository extends JpaRepository<Test_Section,Long> {
    Test_Section findByUuid(UUID testSectionId);

    List<Test_Section>findByUserUUID (UUID userUUID);
}
