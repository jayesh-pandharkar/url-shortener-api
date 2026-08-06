package com.jay.urlshortener.service.impl;

import com.jay.urlshortener.entity.User;
import com.jay.urlshortener.repository.UserRepository;
import com.jay.urlshortener.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }
}
