package com.jay.urlshortener.controller;

import com.jay.urlshortener.dto.RegisterRequest;
import com.jay.urlshortener.dto.UserResponse;
import com.jay.urlshortener.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
   public UserResponse register(@Valid @RequestBody RegisterRequest request) {
       return userService.register(request);
   }

}
