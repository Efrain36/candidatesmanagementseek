package com.seek.candidates.infrastructure.adapters.persistence.repository;

import com.seek.candidates.infrastructure.adapters.persistence.entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

}
