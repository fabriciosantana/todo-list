package com.todo.app.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.todo.app.config.JwtService;
import com.todo.app.domain.model.Role;
import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.UserRepository;
import com.todo.app.web.dto.AuthRequest;
import com.todo.app.web.dto.AuthResponse;
import com.todo.app.web.dto.RegisterRequest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private AuthService authService;

  @Test
  void shouldRegisterUserWithNormalizedEmail() {
    RegisterRequest request = new RegisterRequest(" Fabricio ", "  FABRICIO@EXAMPLE.COM ", "123456");
    when(userRepository.existsByEmail("fabricio@example.com")).thenReturn(false);
    when(passwordEncoder.encode("123456")).thenReturn("encoded-password");
    when(jwtService.generateToken(any(User.class))).thenReturn("jwt-token");
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      User saved = invocation.getArgument(0);
      ReflectionTestUtils.setField(saved, "id", 10L);
      return saved;
    });

    AuthResponse response = authService.register(request);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(captor.capture());
    User savedUser = captor.getValue();

    assertEquals("Fabricio", savedUser.getName());
    assertEquals("fabricio@example.com", savedUser.getEmail());
    assertEquals("encoded-password", savedUser.getPassword());
    assertEquals(Role.ROLE_USER, savedUser.getRole());
    assertEquals("jwt-token", response.token());
    assertEquals("fabricio@example.com", response.user().email());
  }

  @Test
  void shouldRejectRegisterWhenEmailAlreadyExists() {
    RegisterRequest request = new RegisterRequest("Fabricio", "fabricio@example.com", "123456");
    when(userRepository.existsByEmail("fabricio@example.com")).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> authService.register(request));

    assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    assertEquals("409 CONFLICT \"E-mail já cadastrado\"", exception.getMessage());
  }

  @Test
  void shouldAuthenticateAndReturnJwtOnLogin() {
    AuthRequest request = new AuthRequest(" FABRICIO@EXAMPLE.COM ", "123456");
    User user = new User();
    ReflectionTestUtils.setField(user, "id", 11L);
    user.setName("Fabricio");
    user.setEmail("fabricio@example.com");

    when(userRepository.findByEmail("fabricio@example.com")).thenReturn(Optional.of(user));
    when(jwtService.generateToken(user)).thenReturn("jwt-token");

    AuthResponse response = authService.login(request);

    verify(authenticationManager)
        .authenticate(new UsernamePasswordAuthenticationToken("fabricio@example.com", "123456"));
    assertEquals("jwt-token", response.token());
    assertEquals("Fabricio", response.user().name());
    assertEquals("fabricio@example.com", response.user().email());
  }

  @Test
  void shouldRejectLoginWhenUserCannotBeLoadedAfterAuthentication() {
    AuthRequest request = new AuthRequest("fabricio@example.com", "123456");
    when(userRepository.findByEmail("fabricio@example.com")).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> authService.login(request));

    assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
  }
}
