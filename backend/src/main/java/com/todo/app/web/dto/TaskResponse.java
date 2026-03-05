package com.todo.app.web.dto;

import java.time.Instant;

public record TaskResponse(
    Long id,
    String title,
    boolean completed,
    Instant createdAt,
    Instant updatedAt
) {
}
