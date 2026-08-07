package com.jay.urlshortener.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String email;
    private Long id;
    private LocalDateTime createdAt;

}
