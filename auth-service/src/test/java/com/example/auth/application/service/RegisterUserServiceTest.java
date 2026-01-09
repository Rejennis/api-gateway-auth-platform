package com.example.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.auth.application.dto.RegisterUserRequest;
import com.example.auth.application.dto.RegisterUserResponse;
import com.example.auth.application.ports.PasswordHasher;
import com.example.auth.application.ports.UserRepository;
import com.example.auth.domain.user.Email;
import com.example.auth.domain.user.PasswordHash;
import com.example.auth.domain.user.User;
import com.example.auth.domain.user.UserId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class RegisterUserServiceTest {

    @Test
    void registerCreatesUserSavesAndReturnsResponse() {
        InMemoryUserRepository repo = new InMemoryUserRepository();
        PasswordHasher hasher = new TestPasswordHasher();
        RegisterUserService service = new RegisterUserService(repo, hasher);

        RegisterUserRequest request = new RegisterUserRequest("user@example.com", "pw");
        RegisterUserResponse response = service.register(request);

        assertNotNull(response);
        assertNotNull(response.userId());
        assertTrue(!response.userId().isBlank());
        assertEquals("user@example.com", response.email());

        User saved = repo.findByEmail(new Email("user@example.com")).orElseThrow();
        assertEquals(saved.getId().value().toString(), response.userId());
        assertEquals(saved.getEmail().value(), response.email());
        assertEquals("hashed:pw", saved.getPasswordHash().hash());
    }

    @Test
    void registerDuplicateEmailThrowsAndDoesNotOverwriteExistingUser() {
        InMemoryUserRepository repo = new InMemoryUserRepository();
        PasswordHasher hasher = new TestPasswordHasher();
        RegisterUserService service = new RegisterUserService(repo, hasher);

        Email email = new Email("user@example.com");
        User existing = new User(UserId.newid(), email, new PasswordHash("hashed:old"));
        repo.save(existing);

        RegisterUserRequest request = new RegisterUserRequest("user@example.com", "pw");
        assertThrows(IllegalArgumentException.class, () -> service.register(request));

        User stillThere = repo.findByEmail(email).orElseThrow();
        assertEquals(existing.getId().value(), stillThere.getId().value());
        assertEquals("hashed:old", stillThere.getPasswordHash().hash());
    }

    @Test
    void registerInvalidEmailThrowsBeforeSaving() {
        InMemoryUserRepository repo = new InMemoryUserRepository();
        PasswordHasher hasher = new TestPasswordHasher();
        RegisterUserService service = new RegisterUserService(repo, hasher);

        RegisterUserRequest request = new RegisterUserRequest("not-an-email", "pw");
        assertThrows(IllegalArgumentException.class, () -> service.register(request));
        assertTrue(repo.isEmpty());
    }

    private static final class TestPasswordHasher implements PasswordHasher {
        @Override
        public PasswordHash hash(String rawPassword) {
            return new PasswordHash("hashed:" + rawPassword);
        }

        @Override
        public boolean verify(String rawPassword, PasswordHash hashedPassword) {
            return hashedPassword != null && ("hashed:" + rawPassword).equals(hashedPassword.hash());
        }
    }

    private static final class InMemoryUserRepository implements UserRepository {
        private final Map<Email, User> usersByEmail = new HashMap<>();

        @Override
        public boolean existsByEmail(Email email) {
            return usersByEmail.containsKey(email);
        }

        @Override
        public void save(User user) {
            usersByEmail.put(user.getEmail(), user);
        }

        @Override
        public Optional<User> findByEmail(Email email) {
            return Optional.ofNullable(usersByEmail.get(email));
        }

        boolean isEmpty() {
            return usersByEmail.isEmpty();
        }
    }
}
