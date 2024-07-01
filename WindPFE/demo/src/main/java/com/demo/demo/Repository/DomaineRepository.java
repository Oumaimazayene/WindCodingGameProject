package com.demo.demo.Repository;

import com.demo.demo.entity.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DomaineRepository extends JpaRepository<Domaine,Long> {
    @Query("SELECT d.id FROM Domaine d")
    List<Long> findAllDomainIds();
    boolean existsByLangAndVersionAndName(String lang, String version, String name);
    List<Domaine> findByNameContainingIgnoreCase(String name);

}
