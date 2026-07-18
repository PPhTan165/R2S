package org.example.api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Integer supervisorId;
}
