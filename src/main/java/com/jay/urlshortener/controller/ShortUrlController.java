package com.jay.urlshortener.controller;

import com.jay.urlshortener.dto.CreateShortUrlRequest;
import com.jay.urlshortener.dto.ShortUrlResponse;
import com.jay.urlshortener.dto.UpdateShortUrlRequest;
import com.jay.urlshortener.entity.User;
import com.jay.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

@RestController
@RequestMapping("/api/urls")
@SecurityRequirement(name = "bearerAuth")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request,
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        ShortUrlResponse response =
                shortUrlService.createShortUrl(
                        request,
                        user.getUsername()
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/my")
    public ResponseEntity<List<ShortUrlResponse>> getMyUrls(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();

        return ResponseEntity.ok(
                shortUrlService.getMyUrls(username)
        );
    }
    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(
            @PathVariable String shortCode,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        shortUrlService.deleteUrl(
                shortCode,
                user.getUsername()
        );

        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{shortCode}")
    public ResponseEntity<ShortUrlResponse> updateUrl(
            @PathVariable String shortCode,
            @Valid @RequestBody UpdateShortUrlRequest request,
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        ShortUrlResponse response =
                shortUrlService.updateUrl(
                        shortCode,
                        request,
                        user.getUsername()
                );

        return ResponseEntity.ok(response);
    }
}