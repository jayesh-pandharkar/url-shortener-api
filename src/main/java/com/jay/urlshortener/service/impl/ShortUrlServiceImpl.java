package com.jay.urlshortener.service.impl;

import com.jay.urlshortener.dto.CreateShortUrlRequest;
import com.jay.urlshortener.dto.ShortUrlResponse;
import com.jay.urlshortener.dto.UpdateShortUrlRequest;
import com.jay.urlshortener.entity.ShortUrl;
import com.jay.urlshortener.entity.User;
import com.jay.urlshortener.repository.ShortUrlRepository;
import com.jay.urlshortener.repository.UserRepository;
import com.jay.urlshortener.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jay.urlshortener.exception.ShortUrlExpiredException;
import com.jay.urlshortener.exception.ShortUrlNotFoundException;
import com.jay.urlshortener.exception.UnauthorizedUrlAccessException;
import com.jay.urlshortener.exception.UserNotFoundException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final UserRepository userRepository;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int CODE_LENGTH = 6;

    private final SecureRandom random = new SecureRandom();

    public ShortUrlServiceImpl(
            ShortUrlRepository shortUrlRepository,
            UserRepository userRepository
    ) {
        this.shortUrlRepository = shortUrlRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ShortUrlResponse createShortUrl(
            CreateShortUrlRequest request,
            String username
    ) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        String shortCode = generateUniqueShortCode();

        ShortUrl shortUrl = ShortUrl.builder()
                .originalUrl(request.originalUrl())
                .shortCode(shortCode)
                .clickCount(0L)
                .createdAt(LocalDateTime.now())
                .expiresAt(request.expiresAt())
                .user(user)
                .build();

        ShortUrl savedUrl = shortUrlRepository.save(shortUrl);

        return new ShortUrlResponse(
                savedUrl.getId(),
                savedUrl.getOriginalUrl(),
                savedUrl.getShortCode(),
                baseUrl + "/" + savedUrl.getShortCode(),
                savedUrl.getClickCount(),
                savedUrl.getCreatedAt(),
                savedUrl.getExpiresAt()
        );
    }

    private String generateUniqueShortCode() {

        String shortCode;

        do {
            shortCode = generateRandomCode();
        } while (shortUrlRepository.existsByShortCode(shortCode));

        return shortCode;
    }

    private String generateRandomCode() {

        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }

    @Override
    public String redirect(String shortCode) {

        ShortUrl shortUrl = shortUrlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() -> new ShortUrlNotFoundException("Short URL not found"));

        if (shortUrl.getExpiresAt() != null &&
                shortUrl.getExpiresAt().isBefore(LocalDateTime.now())) {

            throw new ShortUrlExpiredException("Short URL has expired");
        }

        shortUrl.setClickCount(shortUrl.getClickCount() + 1);

        shortUrlRepository.save(shortUrl);

        return shortUrl.getOriginalUrl();
    }
    @Override
    public List<ShortUrlResponse> getMyUrls(String username) {

        return shortUrlRepository.findByUser_Username(username)
                .stream()
                .map(url -> new ShortUrlResponse(
                        url.getId(),
                        url.getOriginalUrl(),
                        url.getShortCode(),
                        baseUrl + "/" + url.getShortCode(),
                        url.getClickCount(),
                        url.getCreatedAt(),
                        url.getExpiresAt()
                ))
                .toList();
    }
    @Override
    @Transactional
    public void deleteUrl(String shortCode, String username) {

        ShortUrl shortUrl = shortUrlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException("Short URL not found")
                );

        if (!shortUrl.getUser().getUsername().equals(username)) {
            throw new UnauthorizedUrlAccessException(
                    "You are not allowed to delete this URL"
            );
        }

        shortUrlRepository.delete(shortUrl);
    }
    @Override
    @Transactional
    public ShortUrlResponse updateUrl(
            String shortCode,
            UpdateShortUrlRequest request,
            String username
    ) {

        ShortUrl shortUrl = shortUrlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException("Short URL not found")
                );

        if (!shortUrl.getUser().getUsername().equals(username)) {
            throw new UnauthorizedUrlAccessException(
                    "You are not allowed to update this URL"
            );
        }

        if (request.originalUrl() != null) {
            shortUrl.setOriginalUrl(request.originalUrl());
        }

        if (request.expiresAt() != null) {
            shortUrl.setExpiresAt(request.expiresAt());
        }

        ShortUrl updatedUrl = shortUrlRepository.save(shortUrl);

        return new ShortUrlResponse(
                updatedUrl.getId(),
                updatedUrl.getOriginalUrl(),
                updatedUrl.getShortCode(),
                baseUrl + "/" + updatedUrl.getShortCode(),
                updatedUrl.getClickCount(),
                updatedUrl.getCreatedAt(),
                updatedUrl.getExpiresAt()
        );
    }
}
