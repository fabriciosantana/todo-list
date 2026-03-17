package com.todo.app.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.todo.app.domain.model.User;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

  @Test
  void shouldGenerateAndValidateTokenUsingBase64Secret() {
    JwtService jwtService = new JwtService();
    ReflectionTestUtils.setField(jwtService, "secret",
        Base64.getEncoder().encodeToString("12345678901234567890123456789012".getBytes()));
    ReflectionTestUtils.setField(jwtService, "expiration", 60_000L);

    User user = new User();
    user.setEmail("fabricio@example.com");

    String token = jwtService.generateToken(user);

    assertEquals("fabricio@example.com", jwtService.extractUsername(token));
    assertTrue(jwtService.isTokenValid(token, user));
  }

  @Test
  void shouldGenerateAndValidateTokenUsingPlainTextSecretFallback() {
    JwtService jwtService = new JwtService();
    ReflectionTestUtils.setField(jwtService, "secret", "plain-text-secret-from-hosting");
    ReflectionTestUtils.setField(jwtService, "expiration", 60_000L);

    User user = new User();
    user.setEmail("fallback@example.com");

    String token = jwtService.generateToken(user);

    assertEquals("fallback@example.com", jwtService.extractUsername(token));
    assertTrue(jwtService.isTokenValid(token, user));
    assertFalse(jwtService.isTokenValid(token, createUser("other@example.com")));
  }

  private User createUser(String email) {
    User user = new User();
    user.setEmail(email);
    return user;
  }
}
