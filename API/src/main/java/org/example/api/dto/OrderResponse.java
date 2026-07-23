package org.example.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Integer orderId;
    private LocalDateTime orderDate;
    private Integer customerId;
    private String customerName;
    private Integer employeeId;
    private String employeeName;

    public OrderResponse(Integer orderId, LocalDateTime orderDate, Integer customerId, String customerName, Integer employeeId, String employeeName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
}
