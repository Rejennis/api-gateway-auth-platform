package com.example.auth.domain.user;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class UserIdTest {

    @Test
    void newidGeneratesNonNullRandomId() {
        UserId id1 = UserId.newid();
        UserId id2 = UserId.newid();

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotNull(id1.value());
        assertNotNull(id2.value());
        assertNotEquals(id1.value(), id2.value());
    }

    @Test
    void nullUuidThrows() {
        assertThrows(IllegalArgumentException.class, () -> new UserId(null));
    }
}
