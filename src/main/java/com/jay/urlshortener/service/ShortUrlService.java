package com.jay.urlshortener.service;

import com.jay.urlshortener.dto.CreateShortUrlRequest;
import com.jay.urlshortener.dto.ShortUrlResponse;
import com.jay.urlshortener.dto.UpdateShortUrlRequest;

import java.util.List;

public interface ShortUrlService {
    ShortUrlResponse updateUrl(
            String shortCode,
            UpdateShortUrlRequest request,
            String username
    );

    ShortUrlResponse createShortUrl(
            CreateShortUrlRequest request,
            String username
    );
    String redirect(String shortCode);
    List<ShortUrlResponse> getMyUrls(String username);
    void deleteUrl(String shortCode, String username);
}
