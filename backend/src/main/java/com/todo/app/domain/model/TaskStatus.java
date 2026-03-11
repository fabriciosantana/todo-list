package com.todo.app.domain.model;

public enum TaskStatus {
  A_FAZER,
  FAZENDO,
  CONCLUIDO;

  public boolean isDone() {
    return this == CONCLUIDO;
  }
}
