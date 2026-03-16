package com.todo.app.domain.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskStatusTest {

  @Test
  void shouldReportDoneOnlyForConcludedStatus() {
    assertFalse(TaskStatus.A_FAZER.isDone());
    assertFalse(TaskStatus.FAZENDO.isDone());
    assertTrue(TaskStatus.CONCLUIDO.isDone());
  }
}
