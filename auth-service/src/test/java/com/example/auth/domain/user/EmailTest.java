package com.example.auth.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    private static final String VALID_EMAIL = "user@example.com";

    @Test
    void validEmailIsAccepted() {
        Email email = new Email(VALID_EMAIL);
        assertEquals(VALID_EMAIL, email.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "user@example#com", "user@@example.com"})
    void invalidEmailFormatThrows(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Email(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void blankEmailThrows(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Email(value));
    }

    @Test
    void nullEmailThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }
}
