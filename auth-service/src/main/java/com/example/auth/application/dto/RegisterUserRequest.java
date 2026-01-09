package com.example.auth.application.dto;

public final class RegisterUserRequest {
        private final String email;
        private final String password;

        public RegisterUserRequest(String email, String password) {
                this.email = email;
                this.password = password;
        }

        public String email() {
                return email;
        }

        public String password() {
                return password;
        }
}

