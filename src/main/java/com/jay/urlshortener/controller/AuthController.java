package com.jay.urlshortener.controller;

import com.jay.urlshortener.dto.LoginRequest;
import com.jay.urlshortener.dto.LoginResponse;
import com.jay.urlshortener.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}