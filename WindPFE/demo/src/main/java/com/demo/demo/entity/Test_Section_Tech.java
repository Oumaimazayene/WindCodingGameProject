package com.demo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "test_section_Tech")
@DiscriminatorValue("Technique")

public class Test_Section_Tech  extends  Test_Section{

    @ElementCollection
    List<Difficulty> difficulties;
    @ElementCollection
    List<Integer> publicNumber;
    @ElementCollection
    List<Integer> privateNumber;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "testSectionTech_domain",
            joinColumns = @JoinColumn(name = "testSection_id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id"))
    public List<Domaine> domain = new ArrayList<>();

    public boolean validateListsSize() {
        int size = difficulties.size();
        if (size != publicNumber.size() || size != privateNumber.size() || size != domain.size()) {
            return false;
        }
        return true;
    }

}
