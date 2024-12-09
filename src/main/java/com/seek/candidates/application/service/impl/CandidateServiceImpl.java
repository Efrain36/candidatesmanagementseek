package com.seek.candidates.application.service.impl;

import com.seek.candidates.infrastructure.adapters.persistence.CandidatePersistenceAdapter;
import com.seek.candidates.application.service.ICandidateService;
import com.seek.candidates.domain.exception.NotFoundException;
import com.seek.candidates.domain.model.Candidate;
import com.seek.candidates.domain.dto.CandidateDTO;
import com.seek.candidates.domain.dto.mapper.CandidateMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements ICandidateService {

    private final CandidatePersistenceAdapter candidatePersistenceAdapter;
    private final CandidateMapper candidateMapper;

    @Autowired
    public CandidateServiceImpl(CandidatePersistenceAdapter candidatePersistenceAdapter, CandidateMapper candidateMapper) {
        this.candidatePersistenceAdapter = candidatePersistenceAdapter;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public List<CandidateDTO> getAllCandidates() {
        List<Candidate> candidates = candidatePersistenceAdapter.getAllCandidates();
        return candidates.stream()
            .map(candidateMapper::toDTO)
            .toList();
    }

    @Override
    public CandidateDTO saveCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = candidateMapper.toDomain(candidateDTO);
        return candidateMapper.toDTO(candidatePersistenceAdapter.save(candidate));
    }

    @Override
    public CandidateDTO getCandidateById(Long id) {
        return candidateMapper.toDTO(candidatePersistenceAdapter.findById(id)
            .orElseThrow(() -> new NotFoundException("Candidate not found")));
    }

    @Override
    public CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO) {
        Candidate candidate = candidateMapper.toDomain(candidateDTO);
        return candidateMapper.toDTO(candidatePersistenceAdapter.update(id, candidate));
    }

    @Override
    public void deleteCandidate(Long id) {
        candidatePersistenceAdapter.deleteById(id);
    }

}
