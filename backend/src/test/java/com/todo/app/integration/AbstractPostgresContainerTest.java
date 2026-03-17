package com.todo.app.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractPostgresContainerTest {

  static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
      .withDatabaseName("tododb")
      .withUsername("todo")
      .withPassword("todo");

  static {
    POSTGRES.start();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES::getUsername);
    registry.add("spring.datasource.password", POSTGRES::getPassword);
    registry.add("app.jwt.secret",
        () -> "dGhpcy1pcy1hLXRlc3Qtc2VjcmV0LWtleS1mb3ItdGVzdGNvbnRhaW5lcnM=");
    registry.add("app.jwt.expiration-ms", () -> "86400000");
  }
}
