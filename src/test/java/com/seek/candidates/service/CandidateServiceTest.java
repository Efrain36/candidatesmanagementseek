package com.seek.candidates.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.seek.candidates.domain.dto.CandidateDTO;
import com.seek.candidates.domain.exception.NotFoundException;
import com.seek.candidates.application.service.impl.CandidateServiceImpl;
import com.seek.candidates.infrastructure.adapters.persistence.entity.CandidateEntity;
import com.seek.candidates.infrastructure.adapters.persistence.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class CandidateServiceTest {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private CandidateServiceImpl candidateService;

    @Autowired
    private CandidateRepository candidateRepository;

    private CandidateEntity candidateEntity;

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeEach
    void setUp() {
        candidateRepository.deleteAll();
        candidateEntity = candidateRepository.save(new CandidateEntity(null, "John Doe", "john.doe@example.com", "Male", 5000.0));
        candidateRepository.save(new CandidateEntity(null, "Jane Smith", "jane.smith@example.com", "Female", 6000.0));
    }

    @Test
    void testGetAllCandidates() {
        List<CandidateDTO> candidateDTOs = candidateService.getAllCandidates();

        assertEquals(2, candidateDTOs.size());
        assertEquals("John Doe", candidateDTOs.get(0).getName());
        assertEquals("Jane Smith", candidateDTOs.get(1).getName());
    }

    @Test
    void testSaveCandidate() {
        CandidateDTO candidateDTO = new CandidateDTO(null, "Alice", "alice@example.com", "Female", 7000.0);
        CandidateDTO savedDTO = candidateService.saveCandidate(candidateDTO);

        assertEquals("Alice", savedDTO.getName());
        assertEquals("alice@example.com", savedDTO.getEmail());
    }

    @Test
    void testGetCandidateById() {
        CandidateDTO foundDTO = candidateService.getCandidateById(candidateEntity.getId());

        assertEquals(candidateEntity.getName(), foundDTO.getName());
    }

    @Test
    void testUpdateCandidate() {
        Long candidateId = candidateEntity.getId();
        CandidateDTO candidateDTO = new CandidateDTO(candidateId, "John Updated", "john.updated@example.com", "Male", 8000.0);
        CandidateDTO updatedDTO = candidateService.updateCandidate(candidateId, candidateDTO);

        assertEquals("John Updated", updatedDTO.getName());
    }

    @Test
    void testDeleteCandidate() {
        Long candidateId = candidateEntity.getId();
        candidateService.deleteCandidate(candidateId);
        assertThrows(NotFoundException.class, () -> candidateService.getCandidateById(candidateId));
    }

    @Test
    void testGetCandidateById_NotFound() {
        assertThrows(NotFoundException.class, () -> candidateService.getCandidateById(0L));
    }
}
