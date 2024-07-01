package com.demo.demo.Controller;

import com.demo.demo.Service.CandidateService;
import com.demo.demo.Service.TestService;
import com.demo.demo.dtos.CandidateDTO;
import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Candidate;
import com.demo.demo.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v2/candidats")
public class CandidateController {

    private final CandidateService candidateService;
    private final TestService testService;

    @Autowired
    public CandidateController(CandidateService candidateService, TestService testService) {
        this.candidateService = candidateService;
        this.testService = testService;
    }

    @GetMapping("/{id}")
    public Candidate getCandidateByIdd(@PathVariable Long id) {
        return candidateService.getCandidateById(id);
    }


    @GetMapping("/all")
    public List<CandidateDTO> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createCandidate(@RequestBody CandidateDTO candidateDTO) {
        return ResponseEntity.ok().body(candidateService.createCandidate(candidateDTO));
    }


    @PutMapping("/{id}")

    public CandidateDTO updateCandidate(@PathVariable Long id, @RequestBody CandidateDTO candidateDTO) {
        return candidateService.updateCandidate(id, candidateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
    }

    @DeleteMapping("/candidates")
    public void deleteAllCandidates() {
        candidateService.deleteAllCandidates();
    }

    @PostMapping("/evaluatetest")
    public ResponseEntity<Map<String, Object>> sendEmailToCandidate(
            @RequestParam Long testId,
            @RequestParam String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        Map<String, Object> response = new HashMap<>();

        try {
            candidateService.sendEmailToCandidat(testId, email, firstName, lastName);
            String successMessage = "E-mail envoyé avec succès au candidat.";
            response.put("status", HttpStatus.OK.value());
            response.put("message", successMessage);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/{userUUID}/candidates")
    public ResponseEntity<List<Candidate>> getCandidatesByUserUUID(@PathVariable UUID userUUID) {
        List<Candidate> candidates = candidateService.getCandidatesByUserUUID(userUUID);
        return ResponseEntity.ok(candidates);
    }
}
