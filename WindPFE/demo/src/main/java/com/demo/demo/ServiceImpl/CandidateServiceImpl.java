package com.demo.demo.ServiceImpl;

import com.demo.demo.Repository.CandidateRepository;
import com.demo.demo.Repository.TestRepository;
import com.demo.demo.Repository.TestSectionRepository;
import com.demo.demo.Service.CandidateService;
import com.demo.demo.Service.TestService;
import com.demo.demo.dtos.CandidateDTO;
import com.demo.demo.entity.Candidate;
import com.demo.demo.entity.Test;
import com.demo.demo.entity.Test_Section;
import com.demo.demo.entity.Type;
import com.demo.demo.mappers.CandidateMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final EmailServiceImpl emailService;
private final TestRepository testRepository;
private final TestSectionRepository testSectionRepository;


    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

    @Override
    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.findAll().stream()
                .map(candidateMapper::ToCandidateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        return candidateMapper.ToCandidateDTO(candidateRepository.save(candidateMapper.ToCandidate(candidateDTO)));
    }

    @Override
    public CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO) {
        Optional<Candidate> existingCandidate = candidateRepository.findById(id);
        if (existingCandidate.isPresent()) {
            candidateMapper.updateCandidateFromDTO(candidateDTO, existingCandidate.get());
            return candidateMapper.ToCandidateDTO(candidateRepository.save(existingCandidate.get()));
        }
        return null;
    }

    @Override
    public void deleteCandidate(Long id) {
        try {
            candidateRepository.deleteById(id);
            System.out.println("Candidate deleted successfully with ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Candidate with ID " + id + " not found");
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting Candidate");
            throw e;
        }
    }

    @Override
    public void deleteAllCandidates() {
        try {
            candidateRepository.deleteAll();
            System.out.println("All Candidates deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting all Candidates");
            throw e;
        }
    }
    @Override
    public void sendEmailToCandidat(Long testId, String candidatEmail, String firstName, String lastName) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new IllegalArgumentException("Test not found for ID: " + testId));

        Candidate candidat = candidateRepository.findByEmail(candidatEmail);
        if (candidat == null) {
            candidat = new Candidate();
            candidat.setFirstName(firstName);
            candidat.setLastName(lastName);
            candidat.setEmail(candidatEmail);
        }

        String subject = "Test";
        //String testUrl = "http://localhost:4200/#/test/test-start/" + testId;
        String testUrl = "http://localhost:4200/#/test/start/"+testId ;

        String body = generateEmailBody(firstName, lastName, testUrl);

        emailService.sendEmail(candidatEmail, subject, body);
        test.setCandidate(candidat);
        test.setIsSubmitted(true);
        candidateRepository.save(candidat);
    }

    private String generateEmailBody(String firstName, String lastName, String testUrl) {
            return "Bonjour " + firstName + " " + lastName + ",\n\n" +
                    "Nous sommes heureux de vous informer que vous avez été sélectionné pour passer un test dans le cadre de votre candidature.\n\n" +
                    "Vous pouvez accéder au test en suivant ce lien : " + testUrl + "\n\n" +
                    "Soyez prêt à démontrer vos connaissances et votre expérience lors de cette évaluation. Nous vous encourageons à vous préparer en conséquence.\n\n" +
                    "Nous vous souhaitons beaucoup de succès et restons à votre disposition pour toute question ou préoccupation.\n\n" +
                    "Cordialement,\n\n" +
                    "L'équipe de recrutement";
        }





        @Override
    public void saveCandidat(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> getCandidatesByUserUUID(UUID userUUID) {
        List<Test_Section> testSections = testSectionRepository.findByUserUUID(userUUID);

        List<UUID> testSectionUUIDs = testSections.stream()
                .map(Test_Section::getUuid)
                .collect(Collectors.toList());

        List<Test> tests = testRepository.findByTestSectionTechUUIDIn(testSectionUUIDs);

        List<Candidate> candidates = new ArrayList<>();
        for (Test test : tests) {
            candidates.add(test.getCandidate());
        }
        return candidates;
    }
}