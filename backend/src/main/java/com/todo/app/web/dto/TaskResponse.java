package com.todo.app.web.dto;

import com.todo.app.domain.model.TaskStatus;
import java.time.Instant;

public record TaskResponse(
    Long id,
    String title,
    TaskStatus status,
    boolean archived,
    Instant createdAt,
    Instant updatedAt
) {
}
