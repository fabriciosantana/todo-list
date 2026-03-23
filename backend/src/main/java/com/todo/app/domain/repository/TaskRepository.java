package com.todo.app.domain.repository;

import com.todo.app.domain.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
  List<Task> findAllByOwnerIdAndArchivedOrderByCreatedAtDesc(Long ownerId, boolean archived);
  Optional<Task> findByIdAndOwnerId(Long id, Long ownerId);
}
