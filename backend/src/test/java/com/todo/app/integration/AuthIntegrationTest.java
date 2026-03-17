package com.todo.app.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.todo.app.domain.model.Role;
import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.TaskRepository;
import com.todo.app.domain.repository.UserRepository;
import com.todo.app.web.dto.AuthRequest;
import com.todo.app.web.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTest extends AbstractPostgresContainerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @AfterEach
  void tearDown() {
    taskRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void shouldRegisterUserAndReturnJwt() throws Exception {
    RegisterRequest request = new RegisterRequest("Fabricio", "fabricio@example.com", "123456");

    mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andExpect(jsonPath("$.user.email").value("fabricio@example.com"))
        .andExpect(jsonPath("$.user.name").value("Fabricio"));

    User savedUser = userRepository.findByEmail("fabricio@example.com").orElseThrow();
    org.junit.jupiter.api.Assertions.assertEquals(Role.ROLE_USER, savedUser.getRole());
  }

  @Test
  void shouldLoginExistingUserAndReturnJwt() throws Exception {
    User user = new User();
    user.setName("Fabricio");
    user.setEmail("fabricio@example.com");
    user.setPassword(passwordEncoder.encode("123456"));
    user.setRole(Role.ROLE_USER);
    userRepository.save(user);

    AuthRequest request = new AuthRequest("fabricio@example.com", "123456");

    mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andExpect(jsonPath("$.user.email").value("fabricio@example.com"));
  }
}
