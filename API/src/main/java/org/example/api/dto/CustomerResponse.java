package org.example.api.dto;

import lombok.Getter;

@Getter
public class CustomerResponse {
    private String customerName;
    private String contactName;
    private String address;
    private String city;
    private String postalCode;
    private String country;

    public CustomerResponse(String customerName, String contactName, String address, String city, String postalCode, String country) {
        this.customerName = customerName;
        this.contactName = contactName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }
}
