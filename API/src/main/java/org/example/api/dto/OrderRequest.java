package org.example.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequest {
    private LocalDateTime orderDate;
    private Integer employeeId;
    private Integer customerId;

}
