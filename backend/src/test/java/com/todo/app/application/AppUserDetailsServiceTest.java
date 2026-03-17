package com.todo.app.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AppUserDetailsService service;

  @Test
  void shouldLoadUserByUsername() {
    User user = new User();
    user.setEmail("fabricio@example.com");
    user.setPassword("encoded-password");
    when(userRepository.findByEmail("fabricio@example.com")).thenReturn(Optional.of(user));

    User loaded = (User) service.loadUserByUsername("fabricio@example.com");

    assertEquals("fabricio@example.com", loaded.getUsername());
    assertEquals("encoded-password", loaded.getPassword());
  }

  @Test
  void shouldThrowWhenUserDoesNotExist() {
    when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class,
        () -> service.loadUserByUsername("missing@example.com"));
  }
}
