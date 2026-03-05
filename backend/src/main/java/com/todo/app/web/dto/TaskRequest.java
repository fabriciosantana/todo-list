package com.todo.app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
    @NotBlank @Size(max = 120) String title,
    Boolean completed
) {
}
