package org.example.api.service.impl;

import org.example.api.dto.LoginRequest;
import org.example.api.dto.LoginResponse;
import org.example.api.dto.RegisterRequest;
import org.example.api.dto.RegisterResponse;
import org.example.api.entity.Role;
import org.example.api.entity.User;
import org.example.api.exception.BusinessConflictException;
import org.example.api.exception.BusinessValidationException;
import org.example.api.exception.ResourceNotFoundException;
import org.example.api.repository.RoleRepository;
import org.example.api.repository.UserRepository;
import org.example.api.security.JwtService;
import org.example.api.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public RegisterResponse register(RegisterRequest request){
//       Step 1: Check username exists or not
        if(userRepository.existsByUsername(request.getUsername())){
            throw new BusinessConflictException("Username already exists");
        }

//        Step 2: Find default ROLE_USER
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(()->new ResourceNotFoundException("Default role USER not found"));

//        Step 3: Create a new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

//        Encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(Set.of(userRole));

//        Step 4: save user to DB
        userRepository.save(user);

        return new RegisterResponse("Register successfully");
    }

    @Override
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new BusinessConflictException("Invalid username or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BusinessValidationException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);
        long exp = jwtService.getExpirationSeconds(token);

        return  new LoginResponse(token,exp);
    }
}
