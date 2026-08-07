package com.jay.urlshortener.service.impl;

import com.jay.urlshortener.dto.RegisterRequest;
import com.jay.urlshortener.dto.UserResponse;
import com.jay.urlshortener.entity.User;
import com.jay.urlshortener.repository.UserRepository;
import com.jay.urlshortener.service.UserService;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Builder
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
}
