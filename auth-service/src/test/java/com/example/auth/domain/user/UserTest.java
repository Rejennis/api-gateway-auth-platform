package com.example.auth.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class UserTest {

    private static final Email EMAIL = new Email("user@example.com");
    private static final PasswordHash HASH_1 = new PasswordHash("hash-1");
    private static final PasswordHash HASH_2 = new PasswordHash("hash-2");

    private static User newUser() {
        return new User(UserId.newid(), EMAIL, HASH_1);
    }

    @Test
    void newUserIsActiveByDefault() {
        User user = newUser();
        assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    @Test
    void disableChangesStatusAndSecondDisableThrows() {
        User user = newUser();
        user.disable();
        assertEquals(UserStatus.DISABLED, user.getStatus());
        assertThrows(IllegalStateException.class, user::disable);
    }

    @Test
    void changePasswordUpdatesHash() {
        User user = newUser();
        user.changePassword(HASH_2);
        assertEquals(HASH_2.hash(), user.getPasswordHash().hash());
    }
}
