package com.todo.app.web.dto;

public record AuthResponse(String token, UserResponse user) {
}
