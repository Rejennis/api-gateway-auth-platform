package com.example.auth.application.service;

import com.example.auth.application.dto.RegisterUserRequest;
import com.example.auth.application.dto.RegisterUserResponse;
import com.example.auth.application.ports.PasswordHasher;
import com.example.auth.application.ports.UserRepository;
import com.example.auth.domain.user.Email;
import com.example.auth.domain.user.PasswordHash;
import com.example.auth.domain.user.User;
import com.example.auth.domain.user.UserId;

public class RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public RegisterUserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public RegisterUserResponse register(RegisterUserRequest request) {
        Email email = new Email(request.email());
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }

        PasswordHash hashedPassword = passwordHasher.hash(request.password());
        User newUser = new User(UserId.newid(), email, hashedPassword);
        userRepository.save(newUser);

        return new RegisterUserResponse(
                newUser.getId().value().toString(),
                newUser.getEmail().value()
        );
    }
}
