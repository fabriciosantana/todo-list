import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from './core/auth.service';
import { TaskService } from './core/task.service';
import { Task, User } from './models/types';

@Component({
  selector: 'app-root',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private readonly formBuilder = new FormBuilder();

  authMode: 'login' | 'register' = 'login';
  currentUser: User | null = null;
  tasks: Task[] = [];
  errorMessage = '';
  infoMessage = '';
  loading = false;

  authForm = this.formBuilder.group({
    name: [''],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  taskForm = this.formBuilder.group({
    title: ['', [Validators.required, Validators.maxLength(120)]]
  });

  constructor(
    private readonly authService: AuthService,
    private readonly taskService: TaskService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getUser();

    if (this.authService.isAuthenticated()) {
      this.loadTasks();
    }
  }

  setAuthMode(mode: 'login' | 'register'): void {
    this.authMode = mode;
    this.errorMessage = '';
    this.infoMessage = '';
  }

  submitAuth(): void {
    if (this.authMode === 'register') {
      this.authForm.controls.name.addValidators([Validators.required, Validators.minLength(2)]);
      this.authForm.controls.name.updateValueAndValidity({ emitEvent: false });
    }

    if (this.authForm.invalid) {
      this.authForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const email = this.authForm.value.email ?? '';
    const password = this.authForm.value.password ?? '';

    if (this.authMode === 'register') {
      const name = this.authForm.value.name ?? '';

      this.authService.register({ name, email, password }).subscribe({
        next: (response) => {
          this.currentUser = response.user;
          this.infoMessage = 'Cadastro realizado com sucesso.';
          this.authForm.reset();
          this.loadTasks();
          this.loading = false;
        },
        error: (err) => {
          this.errorMessage = err?.error?.message ?? 'Falha ao cadastrar usuário.';
          this.loading = false;
        }
      });
      return;
    }

    this.authService.login({ email, password }).subscribe({
      next: (response) => {
        this.currentUser = response.user;
        this.infoMessage = 'Login realizado com sucesso.';
        this.authForm.reset();
        this.loadTasks();
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = err?.error?.message ?? 'Falha ao autenticar.';
        this.loading = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.currentUser = null;
    this.tasks = [];
    this.errorMessage = '';
    this.infoMessage = 'Sessão encerrada.';
  }

  loadTasks(): void {
    this.taskService.list().subscribe({
      next: (tasks) => {
        this.tasks = tasks;
      },
      error: () => {
        this.errorMessage = 'Não foi possível carregar tarefas. Faça login novamente.';
        this.logout();
      }
    });
  }

  createTask(): void {
    if (this.taskForm.invalid) {
      this.taskForm.markAllAsTouched();
      return;
    }

    const title = this.taskForm.value.title ?? '';

    this.taskService.create({ title }).subscribe({
      next: (task) => {
        this.tasks = [task, ...this.tasks];
        this.taskForm.reset();
      },
      error: () => {
        this.errorMessage = 'Não foi possível criar a tarefa.';
      }
    });
  }

  toggleTask(taskId: number): void {
    this.taskService.toggle(taskId).subscribe({
      next: (updated) => {
        this.tasks = this.tasks.map((task) => (task.id === taskId ? updated : task));
      },
      error: () => {
        this.errorMessage = 'Não foi possível atualizar a tarefa.';
      }
    });
  }

  editTask(task: Task): void {
    const newTitle = window.prompt('Novo título da tarefa:', task.title);
    if (newTitle === null) {
      return;
    }

    const title = newTitle.trim();
    if (!title) {
      this.errorMessage = 'Título não pode ser vazio.';
      return;
    }

    this.taskService.update(task.id, { title, completed: task.completed }).subscribe({
      next: (updated) => {
        this.tasks = this.tasks.map((item) => (item.id === task.id ? updated : item));
      },
      error: () => {
        this.errorMessage = 'Não foi possível editar a tarefa.';
      }
    });
  }

  deleteTask(taskId: number): void {
    this.taskService.delete(taskId).subscribe({
      next: () => {
        this.tasks = this.tasks.filter((task) => task.id !== taskId);
      },
      error: () => {
        this.errorMessage = 'Não foi possível remover a tarefa.';
      }
    });
  }

  pendingCount(): number {
    return this.tasks.filter((task) => !task.completed).length;
  }
}
