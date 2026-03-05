package com.todo.app.application.service;

import com.todo.app.domain.model.Task;
import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.TaskRepository;
import com.todo.app.domain.repository.UserRepository;
import com.todo.app.web.dto.TaskRequest;
import com.todo.app.web.dto.TaskResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;

  public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public List<TaskResponse> findAllByOwner(Long ownerId) {
    return taskRepository.findAllByOwnerIdOrderByCreatedAtDesc(ownerId)
        .stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional
  public TaskResponse create(Long ownerId, TaskRequest request) {
    User owner = userRepository.findById(ownerId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

    Task task = new Task();
    task.setTitle(request.title().trim());
    task.setCompleted(false);
    task.setOwner(owner);

    return toResponse(taskRepository.save(task));
  }

  @Transactional
  public TaskResponse update(Long ownerId, Long taskId, TaskRequest request) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setTitle(request.title().trim());

    if (request.completed() != null) {
      task.setCompleted(request.completed());
    }

    return toResponse(taskRepository.save(task));
  }

  @Transactional
  public TaskResponse toggle(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setCompleted(!task.isCompleted());
    return toResponse(taskRepository.save(task));
  }

  @Transactional
  public void delete(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    taskRepository.delete(task);
  }

  private Task findOwnerTask(Long ownerId, Long taskId) {
    return taskRepository.findByIdAndOwnerId(taskId, ownerId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
  }

  private TaskResponse toResponse(Task task) {
    return new TaskResponse(
        task.getId(),
        task.getTitle(),
        task.isCompleted(),
        task.getCreatedAt(),
        task.getUpdatedAt());
  }
}
