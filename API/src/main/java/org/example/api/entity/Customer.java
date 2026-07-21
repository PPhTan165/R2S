package org.example.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(nullable = false)
    private String customerName;

    private String contactName;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}
