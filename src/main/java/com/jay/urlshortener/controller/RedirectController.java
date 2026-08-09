package com.jay.urlshortener.controller;

import com.jay.urlshortener.service.ShortUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedirectController {

    private final ShortUrlService shortUrlService;

    public RedirectController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode
    ) {

        String originalUrl = shortUrlService.redirect(shortCode);

        return ResponseEntity
                .status(302)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }
}