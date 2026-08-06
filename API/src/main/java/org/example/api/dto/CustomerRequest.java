package org.example.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class CustomerRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    private String contactName;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}
