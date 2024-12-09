package com.seek.candidates.application.service;

import com.seek.candidates.domain.dto.CandidateDTO;
import java.util.List;

public interface ICandidateService {

    List<CandidateDTO> getAllCandidates();
    CandidateDTO saveCandidate(CandidateDTO candidateDTO);
    CandidateDTO getCandidateById(Long id);
    CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO);
    void deleteCandidate(Long id);

}
