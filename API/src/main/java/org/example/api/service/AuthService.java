package org.example.api.service;

import org.example.api.dto.LoginRequest;
import org.example.api.dto.LoginResponse;
import org.example.api.dto.RegisterRequest;
import org.example.api.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    RegisterResponse registerAdmin(RegisterRequest request);
}
