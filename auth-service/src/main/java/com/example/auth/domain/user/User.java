package com.example.auth.domain.user;

public class User {
    private final UserId userId;
    private final Email email;
    private final PasswordHash passwordHash;
    private UserStatus status;

    public User(UserId userId, Email email, PasswordHash passwordHash) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = UserStatus.ACTIVE;
    }

    public void disable() {
       if (status == UserStatus.DISABLED) {
            throw new IllegalStateException("User already disabled");
        }
        this.status = UserStatus.DISABLED;
    }
    public void changePassword(PasswordHash newHash) {
        this.passwordHash = newHash;
    }
}
