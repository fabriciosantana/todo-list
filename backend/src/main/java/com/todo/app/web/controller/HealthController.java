package com.todo.app.web.controller;

import java.time.Instant;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

  @GetMapping
  public Map<String, Object> health() {
    return Map.of(
        "status", "UP",
        "service", "todo-backend",
        "timestamp", Instant.now().toString());
  }
}
