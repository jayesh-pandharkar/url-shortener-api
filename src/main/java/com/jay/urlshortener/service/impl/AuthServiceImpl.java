package com.jay.urlshortener.service.impl;

import com.jay.urlshortener.dto.LoginRequest;
import com.jay.urlshortener.dto.LoginResponse;
import com.jay.urlshortener.entity.User;
import com.jay.urlshortener.repository.UserRepository;
import com.jay.urlshortener.service.AuthService;
import com.jay.urlshortener.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,  JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
