package com.example.auth.application.ports;

import com.example.auth.domain.user.Email;
import com.example.auth.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(Email email);
    void save(User user);
    Optional<User> findByEmail(Email email);
}
