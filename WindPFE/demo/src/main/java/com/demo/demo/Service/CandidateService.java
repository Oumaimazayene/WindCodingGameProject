package com.demo.demo.Service;

import com.demo.demo.dtos.CandidateDTO;
import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Candidate;

import java.util.List;
import java.util.UUID;

public interface CandidateService {
    Candidate getCandidateById(Long id);
    List<CandidateDTO> getAllCandidates();
    CandidateDTO createCandidate(CandidateDTO candidateDTO);
    CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO);
    void deleteCandidate(Long id);
    void  deleteAllCandidates();
    void saveCandidat(Candidate candidate);
    void sendEmailToCandidat(Long testId, String candidatEmail, String firstName, String lastName);

    List<Candidate> getCandidatesByUserUUID(UUID userUUID);

}
