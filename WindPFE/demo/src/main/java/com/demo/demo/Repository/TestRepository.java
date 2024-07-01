package com.demo.demo.Repository;

import com.demo.demo.entity.Test;
import com.demo.demo.entity.Test_Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TestRepository extends JpaRepository<Test,Long> {
    List <Test>findBytestSectionUUID( UUID testSectionUUId);
    @Query("SELECT t FROM Test t WHERE t.testSectionUUID IN :testSectionUUIDs")
    List<Test> findByTestSectionTechUUIDIn(List<UUID> testSectionUUIDs);
}
