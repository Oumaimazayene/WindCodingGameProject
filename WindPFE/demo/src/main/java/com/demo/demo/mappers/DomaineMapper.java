package com.demo.demo.mappers;

import com.demo.demo.dtos.DomaineDTo;
import com.demo.demo.entity.Candidate;
import com.demo.demo.entity.Domaine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DomaineMapper {
    DomaineDTo ToDomaineDTo(Domaine domaine);
    Domaine ToDomaine (DomaineDTo domaineDTo);

    void updateDomaineFromDTO(DomaineDTo domaineDTo, @MappingTarget Domaine existingDomaine);
}
