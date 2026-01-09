package com.example.auth.domain.user;

public record Email(String value) {
    public Email {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("email cannot be empty");
        }
        if (!value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
    
