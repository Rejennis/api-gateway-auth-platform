package com.example.auth.application.ports;

import com.example.auth.domain.user.PasswordHash;

public interface PasswordHasher {
    PasswordHash hash(String rawPassword);

    boolean verify(String rawPassword, PasswordHash hashedPassword);
}
