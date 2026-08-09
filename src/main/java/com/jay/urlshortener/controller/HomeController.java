package com.jay.urlshortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
                <html>
                <head>
                    <title>URL Shortener API</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            max-width: 800px;
                            margin: 60px auto;
                            padding: 20px;
                            line-height: 1.6;
                        }

                        h1 {
                            margin-bottom: 10px;
                        }

                        .status {
                            color: green;
                            font-weight: bold;
                        }

                        code {
                            background: #f4f4f4;
                            padding: 4px 8px;
                            border-radius: 4px;
                        }

                        a {
                            text-decoration: none;
                        }
                    </style>
                </head>

                <body>

                    <h1>🔗 URL Shortener API</h1>

                    <p class="status">● API is running</p>

                    <p>
                        A production-oriented REST API built with
                        Java, Spring Boot, PostgreSQL, JWT, Docker and Swagger.
                    </p>

                    <h2>API Documentation</h2>

                    <p>
                        <a href="/swagger-ui/index.html">
                            📚 Open Swagger UI
                        </a>
                    </p>

                    <h2>API Endpoints</h2>

                    <ul>
                        <li><code>POST /api/users/register</code> — Register</li>
                        <li><code>POST /api/auth/login</code> — Login</li>
                        <li><code>POST /api/urls</code> — Create short URL</li>
                        <li><code>GET /api/urls/my</code> — Get user's URLs</li>
                        <li><code>PUT /api/urls/{shortCode}</code> — Update URL</li>
                        <li><code>DELETE /api/urls/{shortCode}</code> — Delete URL</li>
                        <li><code>GET /{shortCode}</code> — Redirect</li>
                    </ul>

                    <h2>Technology Stack</h2>

                    <p>
                        Java · Spring Boot · Spring Security · JWT ·
                        PostgreSQL · JPA/Hibernate · Docker · Swagger/OpenAPI
                    </p>

                </body>
                </html>
                """;
    }
}