package com.todo.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.todo.app.domain.model.Role;
import com.todo.app.domain.model.Task;
import com.todo.app.domain.model.TaskStatus;
import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.TaskRepository;
import com.todo.app.domain.repository.UserRepository;
import com.todo.app.integration.AbstractPostgresContainerTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TaskRepositoryIntegrationTest extends AbstractPostgresContainerTest {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserRepository userRepository;

  @AfterEach
  void tearDown() {
    taskRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void shouldFindTasksByOwnerAndArchivedOrderedByCreationDate() {
    User owner = saveUser("owner@example.com");
    User otherOwner = saveUser("other@example.com");

    Task archivedTask = saveTask(owner, "Arquivada", TaskStatus.CONCLUIDO, true);
    Task olderActive = saveTask(owner, "Mais antiga", TaskStatus.A_FAZER, false);
    Task newerActive = saveTask(owner, "Mais nova", TaskStatus.FAZENDO, false);
    saveTask(otherOwner, "Outra pessoa", TaskStatus.A_FAZER, false);

    List<Task> activeTasks = taskRepository.findAllByOwnerIdAndArchivedOrderByCreatedAtDesc(owner.getId(), false);
    List<Task> archivedTasks = taskRepository.findAllByOwnerIdAndArchivedOrderByCreatedAtDesc(owner.getId(), true);
    Optional<Task> ownedTask = taskRepository.findByIdAndOwnerId(newerActive.getId(), owner.getId());

    assertEquals(List.of(newerActive.getId(), olderActive.getId()),
        activeTasks.stream().map(Task::getId).toList());
    assertEquals(List.of(archivedTask.getId()), archivedTasks.stream().map(Task::getId).toList());
    assertTrue(ownedTask.isPresent());
  }

  private User saveUser(String email) {
    User user = new User();
    user.setName(email);
    user.setEmail(email);
    user.setPassword("encoded-password");
    user.setRole(Role.ROLE_USER);
    return userRepository.save(user);
  }

  private Task saveTask(User owner, String title, TaskStatus status, boolean archived) {
    Task task = new Task();
    task.setTitle(title);
    task.setStatus(status);
    task.setArchived(archived);
    task.setOwner(owner);
    return taskRepository.save(task);
  }
}
