package com.jay.urlshortener.dto;

import java.time.LocalDateTime;

public record ShortUrlResponse(
        Long id,
        String originalUrl,
        String shortCode,
        String shortUrl,
        Long clickCount,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {
}