package com.demo.demo.ServiceImpl;

import com.demo.demo.Repository.DomaineRepository;
import com.demo.demo.Service.DomaineSevice;
import com.demo.demo.dtos.DomaineDTo;
import com.demo.demo.entity.Candidate;
import com.demo.demo.entity.Domaine;
import com.demo.demo.mappers.DomaineMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
@Service
public class DomaineServiceImpl implements DomaineSevice {
private  final DomaineRepository domaineRepository;
private final DomaineMapper domaineMapper;

    public DomaineServiceImpl(DomaineRepository domaineRepository, DomaineMapper domaineMapper) {
        this.domaineRepository = domaineRepository;
        this.domaineMapper = domaineMapper;
    }

    @Override
    public Domaine getDomaineById(Long id)  {
        return domaineRepository.findById(id).orElse(null);
    }


    @Override
    public List<DomaineDTo> getAllDomaines() {
        return domaineRepository.findAll().stream()
                .map(domaineMapper::ToDomaineDTo)
                .collect(Collectors.toList());
    }

    public boolean domaineExisteDeja(DomaineDTo domaineDTo) {
        JSONParser parser = new JSONParser();
        JSONArray domainesExistants = null;
        try (Reader reader = new FileReader("C:\\Users\\DELL\\Desktop\\stage pfe\\WindHiring\\WindPFE\\demo\\src\\main\\java\\com\\demo\\demo\\Data\\domains.json")) {
            domainesExistants = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object domaineObj : domainesExistants) {
            JSONObject domaineJson = (JSONObject) domaineObj;
            String lang = (String) domaineJson.get("lang");
            String version = (String) domaineJson.get("version");
            String name = (String) domaineJson.get("name");
            if (lang.equals(domaineDTo.getLang()) && version.equals(domaineDTo.getVersion()) && name.equals(domaineDTo.getName())) {
                return true; // Le domaine existe déjà
            }
        }

        return false; // Le domaine n'existe pas
    }
    @Override

    public DomaineDTo createDomaine(DomaineDTo domaineDTo) {
        if (!domaineExisteDeja(domaineDTo)) {
            System.out.println("Vous ne pouvez pas ajouter ce domaine car il n'existe pas.");
            return null;
        }

        Domaine domaine = domaineMapper.ToDomaine(domaineDTo);
        domaine = domaineRepository.save(domaine);
        return domaineMapper.ToDomaineDTo(domaine);
    }





   /* public DomaineDTo createDomaine(DomaineDTo domaineDTo) {
        if (domaineRepository.existsByLangAndVersionAndName(domaineDTo.getLang(), domaineDTo.getVersion(), domaineDTo.getName())) {
            throw new RuntimeException("Le domaine existe déjà avec les mêmes caractéristiques");
        }
        Domaine domaine = domaineMapper.ToDomaine(domaineDTo);
        domaine = domaineRepository.save(domaine);
        return domaineMapper.ToDomaineDTo(domaine);
    }
*/

    @Override
    public DomaineDTo updateDomaine(Long id, DomaineDTo domaineDTO) {
        Optional<Domaine> existingDomaineOptional = domaineRepository.findById(id);

        if (existingDomaineOptional.isPresent()) {
            Domaine existingDomaine = existingDomaineOptional.get();

            // Vérifier chaque champ et le mettre à jour s'il est différent de null dans le DTO
            if (domaineDTO.getName() != null) {
                existingDomaine.setName(domaineDTO.getName());
            }
            if (domaineDTO.getLang() != null) {
                existingDomaine.setLang(domaineDTO.getLang());
            }
            if (domaineDTO.getVersion() != null) {
                existingDomaine.setVersion(domaineDTO.getVersion());
            }
            return domaineMapper.ToDomaineDTo(domaineRepository.save(existingDomaine));
        } else {
            // Gérer le cas où le domaine avec l'ID spécifié n'existe pas
            return null;
        }
    }


    @Override
    public void deleteDomaine(Long id) {
        try {
            domaineRepository.deleteById(id);
            System.out.println("Domaine deleted successfully with ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Domaine with ID " + id + " not found");
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting Domaine");
            throw e;
        }
    }

    @Override
    public void deleteAllDomaine() {
        try {
            domaineRepository.deleteAll();
            System.out.println("All Domaines deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all Domaines");
            throw e;
        }

    }
    @Override
    public long countDomains() {
        return domaineRepository.count();
    }

@Override
    public List<Domaine> findDomainesByName(String name) {
        return domaineRepository.findByNameContainingIgnoreCase(name);
    }

}
