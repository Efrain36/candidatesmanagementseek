package com.seek.candidates.infrastructure.adapters.persistence;

import com.seek.candidates.domain.exception.NotFoundException;
import com.seek.candidates.domain.model.Candidate;
import com.seek.candidates.domain.dto.mapper.CandidateMapper;
import com.seek.candidates.domain.repository.ICandidateRepository;
import com.seek.candidates.infrastructure.adapters.persistence.entity.CandidateEntity;
import com.seek.candidates.infrastructure.adapters.persistence.repository.CandidateRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CandidatePersistenceAdapter implements ICandidateRepository {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    public CandidatePersistenceAdapter(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public List<Candidate> getAllCandidates() {
        List<CandidateEntity> candidateEntities = candidateRepository.findAll();
        return candidateEntities.stream()
            .map(candidateMapper::toDomain)
            .toList();
    }

    @Override
    public Candidate save(Candidate candidate) {
        CandidateEntity candidateEntity = candidateMapper.toEntity(candidate);
        return candidateMapper.toDomain(candidateRepository.save(candidateEntity));
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id)
            .map(candidateMapper::toDomain);
    }

    @Override
    public Candidate update(Long id, Candidate candidate) {
        if (!candidateRepository.existsById(id)) {
            throw new NotFoundException("Candidate not found");
        }
        CandidateEntity candidateEntity = candidateMapper.toEntity(candidate);
        candidateEntity.setId(id);
        return candidateMapper.toDomain(candidateRepository.save(candidateEntity));
    }

    @Override
    public void deleteById(Long id) {
        candidateRepository.deleteById(id);
    }

}
