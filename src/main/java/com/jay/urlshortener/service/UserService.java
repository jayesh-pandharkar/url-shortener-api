package com.jay.urlshortener.service;

import com.jay.urlshortener.dto.RegisterRequest;
import com.jay.urlshortener.dto.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

}
