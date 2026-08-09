package com.jay.urlshortener.repository;

import com.jay.urlshortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    boolean existsByShortCode(String shortCode);

    Optional<ShortUrl> findByShortCode(String shortCode);

    List<ShortUrl> findByUser_Username(String username);

    void deleteByShortCode(String shortCode);
}