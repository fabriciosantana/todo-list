package com.todo.app.web.dto;

import com.todo.app.domain.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
    @NotBlank @Size(max = 120) String title,
    TaskStatus status
) {
}
