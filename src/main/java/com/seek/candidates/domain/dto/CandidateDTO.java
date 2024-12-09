package com.seek.candidates.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private Double expectedSalary;
}
