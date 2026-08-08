package com.jay.urlshortener.service;

import com.jay.urlshortener.dto.LoginRequest;
import com.jay.urlshortener.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

}
