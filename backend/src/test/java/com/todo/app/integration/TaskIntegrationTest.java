package com.todo.app.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.todo.app.domain.model.Role;
import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.TaskRepository;
import com.todo.app.domain.repository.UserRepository;
import com.todo.app.web.dto.AuthRequest;
import com.todo.app.web.dto.TaskRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class TaskIntegrationTest extends AbstractPostgresContainerTest {

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
  void shouldRejectListingTasksWithoutAuthentication() throws Exception {
    mockMvc.perform(get("/api/tasks"))
        .andExpect(status().isForbidden());
  }

  @Test
  void shouldCreateListArchiveAndUnarchiveTasksForAuthenticatedUser() throws Exception {
    User user = new User();
    user.setName("Fabricio");
    user.setEmail("fabricio@example.com");
    user.setPassword(passwordEncoder.encode("123456"));
    user.setRole(Role.ROLE_USER);
    userRepository.save(user);

    String token = loginAndGetToken("fabricio@example.com", "123456");

    MvcResult createResult = mockMvc.perform(post("/api/tasks")
            .header(HttpHeaders.AUTHORIZATION, bearer(token))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new TaskRequest("Cobrir integração", null))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.title").value("Cobrir integração"))
        .andExpect(jsonPath("$.status").value("A_FAZER"))
        .andExpect(jsonPath("$.archived").value(false))
        .andReturn();

    Long taskId = extractId(createResult);

    mockMvc.perform(get("/api/tasks")
            .header(HttpHeaders.AUTHORIZATION, bearer(token)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id").value(taskId));

    mockMvc.perform(get("/api/tasks")
            .param("search", "cobrir")
            .param("status", "A_FAZER")
            .param("sortBy", "title")
            .param("sortDirection", "asc")
            .header(HttpHeaders.AUTHORIZATION, bearer(token)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].title").value("Cobrir integração"));

    mockMvc.perform(patch("/api/tasks/{id}/archive", taskId)
            .header(HttpHeaders.AUTHORIZATION, bearer(token)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.archived").value(true));

    mockMvc.perform(get("/api/tasks").param("archived", "true")
            .header(HttpHeaders.AUTHORIZATION, bearer(token)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].archived").value(true));

    mockMvc.perform(patch("/api/tasks/{id}/unarchive", taskId)
            .header(HttpHeaders.AUTHORIZATION, bearer(token)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.archived").value(false));
  }

  private String loginAndGetToken(String email, String password) throws Exception {
    MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new AuthRequest(email, password))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andReturn();

    JsonNode body = objectMapper.readTree(loginResult.getResponse().getContentAsString());
    return body.get("token").asText();
  }

  private Long extractId(MvcResult result) throws Exception {
    JsonNode body = objectMapper.readTree(result.getResponse().getContentAsString());
    return body.get("id").asLong();
  }

  private String bearer(String token) {
    return "Bearer " + token;
  }
}
