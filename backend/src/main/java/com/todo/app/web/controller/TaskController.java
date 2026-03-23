package com.todo.app.web.controller;

import com.todo.app.application.service.TaskService;
import com.todo.app.domain.model.TaskStatus;
import com.todo.app.domain.model.User;
import com.todo.app.web.dto.TaskRequest;
import com.todo.app.web.dto.TaskResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public List<TaskResponse> list(
      @AuthenticationPrincipal User currentUser,
      @RequestParam(defaultValue = "false") boolean archived,
      @RequestParam(required = false) String search,
      @RequestParam(required = false) List<TaskStatus> status,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String sortDirection) {
    return taskService.findAllByOwner(currentUser.getId(), archived, search, status, sortBy, sortDirection);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponse create(@AuthenticationPrincipal User currentUser,
      @Valid @RequestBody TaskRequest request) {
    return taskService.create(currentUser.getId(), request);
  }

  @PutMapping("/{id}")
  public TaskResponse update(@AuthenticationPrincipal User currentUser,
      @PathVariable Long id,
      @Valid @RequestBody TaskRequest request) {
    return taskService.update(currentUser.getId(), id, request);
  }

  @PatchMapping("/{id}/toggle")
  public TaskResponse toggle(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {
    return taskService.toggle(currentUser.getId(), id);
  }

  @PatchMapping("/{id}/archive")
  public TaskResponse archive(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {
    return taskService.archive(currentUser.getId(), id);
  }

  @PatchMapping("/{id}/unarchive")
  public TaskResponse unarchive(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {
    return taskService.unarchive(currentUser.getId(), id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@AuthenticationPrincipal User currentUser, @PathVariable Long id) {
    taskService.delete(currentUser.getId(), id);
  }
}
