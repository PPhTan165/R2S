package org.example.api.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(length = 10,nullable = false)
    private String firstName;

    @Column(length = 20, nullable = false)
    private String lastName;

    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;
}
