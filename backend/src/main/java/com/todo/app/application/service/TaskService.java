package com.todo.app.application.service;

import com.todo.app.domain.model.Task;
import com.todo.app.domain.model.TaskStatus;
import com.todo.app.domain.model.User;
import com.todo.app.domain.repository.TaskRepository;
import com.todo.app.domain.repository.UserRepository;
import com.todo.app.web.dto.TaskRequest;
import com.todo.app.web.dto.TaskResponse;
import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.criteria.Predicate;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

  private static final Logger log = LoggerFactory.getLogger(TaskService.class);

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;

  public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  @Observed(name = "todo.tasks.find-all", contextualName = "tasks-find-all")
  public List<TaskResponse> findAllByOwner(
      Long ownerId,
      boolean archived,
      String search,
      List<TaskStatus> statuses,
      String sortBy,
      String sortDirection) {
    return taskRepository.findAll(
            buildTaskSpecification(ownerId, archived, search, statuses),
            buildSort(sortBy, sortDirection))
        .stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional
  @Observed(name = "todo.tasks.create", contextualName = "tasks-create")
  public TaskResponse create(Long ownerId, TaskRequest request) {
    User owner = userRepository.findById(ownerId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

    Task task = new Task();
    task.setTitle(request.title().trim());
    task.setStatus(resolveStatus(request.status()));
    task.setArchived(false);
    task.setOwner(owner);

    Task saved = taskRepository.save(task);
    log.info("Task created ownerId={} taskId={} status={}", ownerId, saved.getId(), saved.getStatus());
    return toResponse(saved);
  }

  @Transactional
  @Observed(name = "todo.tasks.update", contextualName = "tasks-update")
  public TaskResponse update(Long ownerId, Long taskId, TaskRequest request) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setTitle(request.title().trim());

    if (request.status() != null) {
      task.setStatus(request.status());
    }

    Task saved = taskRepository.save(task);
    log.info("Task updated ownerId={} taskId={} status={}", ownerId, saved.getId(), saved.getStatus());
    return toResponse(saved);
  }

  @Transactional
  @Observed(name = "todo.tasks.toggle", contextualName = "tasks-toggle")
  public TaskResponse toggle(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setStatus(task.getStatus().isDone() ? TaskStatus.A_FAZER : TaskStatus.CONCLUIDO);
    Task saved = taskRepository.save(task);
    log.info("Task toggled ownerId={} taskId={} status={}", ownerId, saved.getId(), saved.getStatus());
    return toResponse(saved);
  }

  @Transactional
  @Observed(name = "todo.tasks.archive", contextualName = "tasks-archive")
  public TaskResponse archive(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setArchived(true);
    Task saved = taskRepository.save(task);
    log.info("Task archived ownerId={} taskId={}", ownerId, saved.getId());
    return toResponse(saved);
  }

  @Transactional
  @Observed(name = "todo.tasks.unarchive", contextualName = "tasks-unarchive")
  public TaskResponse unarchive(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    task.setArchived(false);
    Task saved = taskRepository.save(task);
    log.info("Task unarchived ownerId={} taskId={}", ownerId, saved.getId());
    return toResponse(saved);
  }

  @Transactional
  @Observed(name = "todo.tasks.delete", contextualName = "tasks-delete")
  public void delete(Long ownerId, Long taskId) {
    Task task = findOwnerTask(ownerId, taskId);
    taskRepository.delete(task);
    log.info("Task deleted ownerId={} taskId={}", ownerId, taskId);
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

  private Specification<Task> buildTaskSpecification(
      Long ownerId,
      boolean archived,
      String search,
      List<TaskStatus> statuses) {
    return (root, query, criteriaBuilder) -> {
      Predicate predicate = criteriaBuilder.and(
          criteriaBuilder.equal(root.get("owner").get("id"), ownerId),
          criteriaBuilder.equal(root.get("archived"), archived));

      if (search != null && !search.isBlank()) {
        predicate = criteriaBuilder.and(
            predicate,
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("title")),
                "%" + search.trim().toLowerCase(Locale.ROOT) + "%"));
      }

      if (statuses != null && !statuses.isEmpty()) {
        predicate = criteriaBuilder.and(predicate, root.get("status").in(statuses));
      }

      return predicate;
    };
  }

  private Sort buildSort(String sortBy, String sortDirection) {
    Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
    return Sort.by(direction, resolveSortProperty(sortBy));
  }

  private String resolveSortProperty(String sortBy) {
    if (sortBy == null) {
      return "createdAt";
    }

    return switch (sortBy) {
      case "updatedAt" -> "updatedAt";
      case "title" -> "title";
      case "createdAt" -> "createdAt";
      default -> "createdAt";
    };
  }
}
