package com.example.auth.application.dto;

public final class RegisterUserResponse {
        private final String userId;
        private final String email;

        public RegisterUserResponse(String userId, String email) {
                this.userId = userId;
                this.email = email;
        }

        public String userId() {
                return userId;
        }

        public String email() {
                return email;
        }
}

