package com.example.auth.domain.user;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
    }
    public static UserId newid() {
        return new UserId(UUID.randomUUID());
    }
    
    
}