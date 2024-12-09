package com.seek.candidates.controller;

import com.seek.candidates.application.controller.CandidateController;
import com.seek.candidates.domain.dto.CandidateDTO;
import com.seek.candidates.domain.exception.NotFoundException;
import com.seek.candidates.infrastructure.adapters.persistence.entity.CandidateEntity;
import com.seek.candidates.infrastructure.adapters.persistence.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class CandidateControllerTest {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private CandidateController candidateController;

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
    public void setup() {
        candidateRepository.deleteAll();
        candidateEntity = candidateRepository.save(new CandidateEntity(null, "John Doe", "john.doe@example.com", "Male", 50000.0));
        candidateRepository.save(new CandidateEntity(null, "Jane Smith", "jane.smith@example.com", "Female", 60000.0));
    }

    @Test
    void testGetAllCandidates_EmptyList() {
        candidateRepository.deleteAll();
        ResponseEntity<List<CandidateDTO>> response = candidateController.getAllCandidates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    void testGetAllCandidates_NonEmptyList() {
        ResponseEntity<List<CandidateDTO>> response = candidateController.getAllCandidates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testCreateCandidate() {
        CandidateDTO candidateDTO = new CandidateDTO(null, "Alice", "alice@example.com", "Female", 70000.0);
        ResponseEntity<CandidateDTO> response = candidateController.createCandidate(candidateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(candidateDTO.getName(), response.getBody().getName());
    }

    @Test
    void testGetCandidateByIdFound() {
        CandidateDTO foundDTO = candidateController.getCandidateById(candidateEntity.getId()).getBody();

        assertNotNull(foundDTO);
        assertEquals(candidateEntity.getName(), foundDTO.getName());
    }

    @Test
    void testGetCandidateByIdNotFound() {
        assertThrows(NotFoundException.class, () -> candidateController.getCandidateById(0L));
    }

    @Test
    void testUpdateCandidate() {
        Long candidateId = candidateEntity.getId();
        CandidateDTO updatedDTO = new CandidateDTO(candidateId, "John Updated", "john.updated@example.com", "Male", 80000.0);
        ResponseEntity<CandidateDTO> response = candidateController.updateCandidate(candidateId, updatedDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedDTO.getName(), response.getBody().getName());
    }

    @Test
    void testUpdateCandidateNotFound() {
        CandidateDTO updatedDTO = new CandidateDTO(0L, "Nonexistent", "nonexistent@example.com", "Male", 80000.0);
        assertThrows(NotFoundException.class, () -> candidateController.updateCandidate(0L, updatedDTO));
    }

    @Test
    void testDeleteCandidate() {
        Long candidateId = candidateEntity.getId();
        candidateController.deleteCandidate(candidateId);

        assertThrows(NotFoundException.class, () -> candidateController.getCandidateById(candidateId));
    }
}
