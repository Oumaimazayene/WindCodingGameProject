package com.demo.demo.Service;

import com.demo.demo.dtos.DomaineDTo;
import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Domaine;
import com.demo.demo.entity.Type;

import java.util.List;

public interface  DomaineSevice {
    Domaine getDomaineById(Long id);
    List<DomaineDTo> getAllDomaines();
    DomaineDTo createDomaine(DomaineDTo domaineDTo);
    DomaineDTo updateDomaine(Long id, DomaineDTo domaineDTo);
    void deleteDomaine(Long id);
    void  deleteAllDomaine();
    long countDomains();
    List<Domaine> findDomainesByName(String name);

}
