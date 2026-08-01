package org.example.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter

public class RegisterResponse {
    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }
}
