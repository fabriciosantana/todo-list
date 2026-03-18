package com.todo.app.application.service;

import com.todo.app.config.JwtService;
import com.todo.app.domain.model.Role;
import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.UserRepository;
import com.todo.app.web.dto.AuthRequest;
import com.todo.app.web.dto.AuthResponse;
import com.todo.app.web.dto.RegisterRequest;
import com.todo.app.web.dto.UserResponse;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private static final Logger log = LoggerFactory.getLogger(AuthService.class);

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @Observed(name = "todo.auth.register", contextualName = "auth-register")
  public AuthResponse register(RegisterRequest request) {
    String email = request.email().trim().toLowerCase();

    if (userRepository.existsByEmail(email)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
    }

    User user = new User();
    user.setName(request.name().trim());
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRole(Role.ROLE_USER);

    User saved = userRepository.save(user);
    log.info("User registered userId={} email={}", saved.getId(), saved.getEmail());

    return new AuthResponse(
        jwtService.generateToken(saved),
        new UserResponse(saved.getId(), saved.getName(), saved.getEmail()));
  }

  @Observed(name = "todo.auth.login", contextualName = "auth-login")
  public AuthResponse login(AuthRequest request) {
    String email = request.email().trim().toLowerCase();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, request.password()));

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));
    log.info("User authenticated userId={} email={}", user.getId(), user.getEmail());

    return new AuthResponse(
        jwtService.generateToken(user),
        new UserResponse(user.getId(), user.getName(), user.getEmail()));
  }
}
