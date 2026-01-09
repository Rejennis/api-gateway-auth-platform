package com.example.auth.domain.user;

public record UserRegisteredEvent(
    UserId userId,
    Email email
) {}
