package com.example.auth.domain.user;

public record PasswordHash(String hash) {
    public PasswordHash {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
    }
}