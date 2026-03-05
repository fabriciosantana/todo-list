package com.todo.app.domain.repository;

import com.todo.app.domain.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findAllByOwnerIdOrderByCreatedAtDesc(Long ownerId);
  Optional<Task> findByIdAndOwnerId(Long id, Long ownerId);
}
