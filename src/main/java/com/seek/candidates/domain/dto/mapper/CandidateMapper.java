package com.seek.candidates.domain.dto.mapper;

import com.seek.candidates.domain.dto.CandidateDTO;
import com.seek.candidates.domain.model.Candidate;
import com.seek.candidates.infrastructure.adapters.persistence.entity.CandidateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    CandidateDTO toDTO(Candidate candidate);
    Candidate toDomain(CandidateDTO candidateDTO);
    CandidateEntity toEntity(Candidate candidate);
    Candidate toDomain(CandidateEntity candidateEntity);

}
