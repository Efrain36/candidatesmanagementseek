package com.seek.candidates.domain.repository;

import com.seek.candidates.domain.model.Candidate;

import java.util.List;
import java.util.Optional;

public interface ICandidateRepository {
    List<Candidate> getAllCandidates();
    Candidate save(Candidate candidate);
    Optional<Candidate> findById(Long id);
     Candidate update(Long id, Candidate candidate)  ;
    void deleteById(Long id);
}
