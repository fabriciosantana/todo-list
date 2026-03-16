package com.todo.app.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.todo.app.domain.model.Task;
import com.todo.app.domain.model.TaskStatus;
import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.TaskRepository;
import com.todo.app.domain.repository.UserRepository;
import com.todo.app.web.dto.TaskRequest;
import com.todo.app.web.dto.TaskResponse;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private TaskService taskService;

  private User owner;

  @BeforeEach
  void setUp() {
    owner = new User();
    owner.setName("Fabricio");
    owner.setEmail("fabricio@example.com");
  }

  @Test
  void shouldCreateTaskWithDefaultStatusWhenRequestOmitsStatus() {
    TaskRequest request = new TaskRequest("Nova tarefa", null);

    when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
    when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
      Task task = invocation.getArgument(0);
      task.setArchived(false);
      return task;
    });

    TaskResponse response = taskService.create(1L, request);

    assertEquals(TaskStatus.A_FAZER, response.status());
    assertFalse(response.archived());
    verify(taskRepository).save(any(Task.class));
  }

  @Test
  void shouldArchiveAndUnarchiveTaskOwnedByUser() {
    Task task = new Task();
    task.setTitle("Arquivar depois");
    task.setStatus(TaskStatus.FAZENDO);
    task.setOwner(owner);

    when(taskRepository.findByIdAndOwnerId(7L, 1L)).thenReturn(Optional.of(task));
    when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

    TaskResponse archived = taskService.archive(1L, 7L);
    TaskResponse restored = taskService.unarchive(1L, 7L);

    assertTrue(archived.archived());
    assertFalse(restored.archived());
  }
}
