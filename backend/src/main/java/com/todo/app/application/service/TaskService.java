package com.todo.app.application.service;

import com.todo.app.domain.model.Task;
import com.todo.app.domain.model.TaskStatus;
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
  public List<TaskResponse> findAllByOwner(Long ownerId, boolean archived) {
    return taskRepository.findAllByOwnerIdAndArchivedOrderByCreatedAtDesc(ownerId, archived)
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
    task.setStatus(resolveStatus(request.status()));
    task.setArchived(false);
    task.setOwner(owner);

    return toResponse(taskRepository.save(task));
  }

  @Transactional
  public TaskResponse update(Long ownerId, Long taskId, TaskRequest request) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setTitle(request.title().trim());

    if (request.status() != null) {
      task.setStatus(request.status());
    }

    return toResponse(taskRepository.save(task));
  }

  @Transactional
  public TaskResponse toggle(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setStatus(task.getStatus().isDone() ? TaskStatus.A_FAZER : TaskStatus.CONCLUIDO);
    return toResponse(taskRepository.save(task));
  }

  @Transactional
  public TaskResponse archive(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setArchived(true);
    return toResponse(taskRepository.save(task));
  }

  @Transactional
  public TaskResponse unarchive(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setArchived(false);
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
        task.getStatus(),
        task.isArchived(),
        task.getCreatedAt(),
        task.getUpdatedAt());
  }

  private TaskStatus resolveStatus(TaskStatus status) {
    return status == null ? TaskStatus.A_FAZER : status;
  }
}
