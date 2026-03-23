import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from './core/auth.service';
import { TaskListQuery, TaskService } from './core/task.service';
import { Task, TaskRequest, TaskStatus, User } from './models/types';

interface StatusOption {
  value: TaskStatus;
  label: string;
}

type NoticeType = 'success' | 'error';
type ViewMode = 'table' | 'kanban';
type TaskScope = 'active' | 'archived';

@Component({
  selector: 'app-root',
  imports: [CommonModule, ReactiveFormsModule, DatePipe],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private readonly formBuilder = new FormBuilder();

  readonly statusOptions: StatusOption[] = [
    { value: 'A_FAZER', label: 'A Fazer' },
    { value: 'FAZENDO', label: 'Fazendo' },
    { value: 'CONCLUIDO', label: 'Concluído' }
  ];

  readonly kanbanColumns: TaskStatus[] = ['A_FAZER', 'FAZENDO', 'CONCLUIDO'];

  authMode: 'login' | 'register' = 'login';
  currentUser: User | null = null;
  tasks: Task[] = [];
  archivedTasks: Task[] = [];
  viewMode: ViewMode = 'table';
  taskScope: TaskScope = 'active';
  draggedTaskId: number | null = null;
  dragOverColumn: TaskStatus | null = null;
  filterState: Required<Pick<TaskListQuery, 'search' | 'sortBy' | 'sortDirection'>> & { statuses: TaskStatus[] } = {
    search: '',
    statuses: [],
    sortBy: 'createdAt',
    sortDirection: 'desc'
  };
  searchFilterOpen = false;
  statusFilterOpen = false;
  notice: { type: NoticeType; message: string } | null = null;
  noticeCountdown = 0;
  loading = false;
  private noticeTimeoutId: ReturnType<typeof setTimeout> | null = null;
  private noticeIntervalId: ReturnType<typeof setInterval> | null = null;

  authForm = this.formBuilder.group({
    name: [''],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  taskForm = this.formBuilder.group({
    title: ['', [Validators.required, Validators.maxLength(120)]],
    status: ['A_FAZER' as TaskStatus, [Validators.required]]
  });

  constructor(
    private readonly authService: AuthService,
    private readonly taskService: TaskService
  ) {}

  ngOnInit(): void {
    this.syncAuthModeValidators();
    this.currentUser = this.authService.getUser();

    if (this.authService.isAuthenticated()) {
      this.loadTasks();
    }
  }

  setAuthMode(mode: 'login' | 'register'): void {
    this.authMode = mode;
    this.syncAuthModeValidators();
    this.clearNotice();
  }

  setViewMode(mode: ViewMode): void {
    this.viewMode = mode;
  }

  setTaskScope(scope: TaskScope): void {
    this.taskScope = scope;
    if (scope === 'archived') {
      this.viewMode = 'table';
    }
    this.loadTasks();
  }

  updateSearchTerm(value: string): void {
    this.filterState.search = value;
    this.loadTasks();
  }

  toggleSearchFilterMenu(): void {
    this.searchFilterOpen = !this.searchFilterOpen;
  }

  toggleStatusFilterMenu(): void {
    this.statusFilterOpen = !this.statusFilterOpen;
  }

  toggleStatusSelection(status: TaskStatus): void {
    if (this.filterState.statuses.includes(status)) {
      this.filterState.statuses = this.filterState.statuses.filter((value) => value !== status);
    } else {
      this.filterState.statuses = [...this.filterState.statuses, status];
    }

    this.loadTasks();
  }

  clearStatusFilter(): void {
    this.filterState.statuses = [];
    this.loadTasks();
  }

  toggleSort(value: NonNullable<TaskListQuery['sortBy']>): void {
    if (this.filterState.sortBy === value) {
      this.filterState.sortDirection = this.filterState.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.filterState.sortBy = value;
      this.filterState.sortDirection = value === 'title' ? 'asc' : 'desc';
    }

    this.loadTasks();
  }

  sortIndicator(value: NonNullable<TaskListQuery['sortBy']>): string {
    if (this.filterState.sortBy !== value) {
      return '';
    }

    return this.filterState.sortDirection === 'asc' ? '↑' : '↓';
  }

  isStatusSelected(status: TaskStatus): boolean {
    return this.filterState.statuses.includes(status);
  }

  submitAuth(): void {
    if (this.authForm.invalid) {
      this.authForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.clearNotice();

    const email = this.authForm.value.email ?? '';
    const password = this.authForm.value.password ?? '';

    if (this.authMode === 'register') {
      const name = this.authForm.value.name ?? '';

      this.authService.register({ name, email, password }).subscribe({
        next: (response) => {
          this.currentUser = response.user;
          this.showNotice('success', 'Cadastro realizado com sucesso.');
          this.authForm.reset();
          this.loadTasks();
          this.loading = false;
        },
        error: (err) => {
          this.showNotice('error', err?.error?.message ?? 'Falha ao cadastrar usuário.');
          this.loading = false;
        }
      });
      return;
    }

    this.authService.login({ email, password }).subscribe({
      next: (response) => {
        this.currentUser = response.user;
        this.showNotice('success', 'Login realizado com sucesso.');
        this.authForm.reset();
        this.loadTasks();
        this.loading = false;
      },
      error: (err) => {
        this.showNotice('error', err?.error?.message ?? 'Falha ao autenticar.');
        this.loading = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.currentUser = null;
    this.tasks = [];
    this.archivedTasks = [];
    this.showNotice('success', 'Sessão encerrada.');
  }

  loadTasks(): void {
    const activeQuery = this.buildTaskQuery();
    const archivedQuery = this.buildTaskQuery();

    this.taskService.list(false, activeQuery).subscribe({
      next: (tasks) => {
        this.tasks = tasks;
      },
      error: () => {
        this.showNotice('error', 'Não foi possível carregar tarefas. Faça login novamente.');
        this.logout();
      }
    });

    this.taskService.list(true, archivedQuery).subscribe({
      next: (tasks) => {
        this.archivedTasks = tasks;
      },
      error: () => {
        this.showNotice('error', 'Não foi possível carregar tarefas arquivadas.');
      }
    });
  }

  createTask(): void {
    if (this.taskForm.invalid) {
      this.taskForm.markAllAsTouched();
      return;
    }

    const payload: TaskRequest = {
      title: this.taskForm.value.title ?? '',
      status: (this.taskForm.value.status as TaskStatus | null) ?? 'A_FAZER'
    };

    this.taskService.create(payload).subscribe({
      next: () => {
        this.taskForm.reset({ title: '', status: 'A_FAZER' });
        this.loadTasks();
      },
      error: () => {
        this.showNotice('error', 'Não foi possível criar a tarefa.');
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
      this.showNotice('error', 'Título não pode ser vazio.');
      return;
    }

    this.updateTask(task, { title, status: task.status }, 'Não foi possível editar a tarefa.');
  }

  updateTaskStatus(task: Task, status: TaskStatus): void {
    if (task.status === status) {
      return;
    }

    this.updateTask(task, { title: task.title, status }, 'Não foi possível atualizar o status da tarefa.');
  }

  toggleTaskDone(task: Task): void {
    this.taskService.toggle(task.id).subscribe({
      next: () => {
        this.loadTasks();
      },
      error: () => {
        this.showNotice('error', 'Não foi possível atualizar o status da tarefa.');
      }
    });
  }

  archiveTask(taskId: number): void {
    this.taskService.archive(taskId).subscribe({
      next: () => {
        this.loadTasks();
        this.showNotice('success', 'Tarefa arquivada.');
      },
      error: () => {
        this.showNotice('error', 'Não foi possível arquivar a tarefa.');
      }
    });
  }

  unarchiveTask(taskId: number): void {
    this.taskService.unarchive(taskId).subscribe({
      next: () => {
        this.loadTasks();
        this.showNotice('success', 'Tarefa desarquivada.');
      },
      error: () => {
        this.showNotice('error', 'Não foi possível desarquivar a tarefa.');
      }
    });
  }

  deleteTask(task: Task): void {
    if (!task.archived) {
      this.showNotice('success', 'Arquive a tarefa antes de excluí-la definitivamente.');
      return;
    }

    const confirmed = window.confirm(
      `Excluir permanentemente a tarefa "${task.title}"?\n\nEssa ação é irreversível.`
    );

    if (!confirmed) {
      return;
    }

    this.taskService.delete(task.id).subscribe({
      next: () => {
        this.loadTasks();
        this.showNotice('success', 'Tarefa removida permanentemente.');
      },
      error: () => {
        this.showNotice('error', 'Não foi possível remover a tarefa.');
      }
    });
  }

  pendingCount(): number {
    return this.tasks.filter((task) => task.status !== 'CONCLUIDO').length;
  }

  tasksByStatus(status: TaskStatus): Task[] {
    return this.tasks.filter((task) => task.status === status);
  }

  statusLabel(status: TaskStatus): string {
    return this.statusOptions.find((option) => option.value === status)?.label ?? status;
  }

  statusBadgeClass(status: TaskStatus): string {
    switch (status) {
      case 'A_FAZER':
        return 'text-bg-secondary';
      case 'FAZENDO':
        return 'text-bg-warning';
      case 'CONCLUIDO':
        return 'text-bg-success';
    }
  }

  trackByTaskId(_: number, task: Task): number {
    return task.id;
  }

  startTaskDrag(task: Task): void {
    this.draggedTaskId = task.id;
    this.dragOverColumn = task.status;
  }

  endTaskDrag(): void {
    this.draggedTaskId = null;
    this.dragOverColumn = null;
  }

  allowKanbanDrop(event: DragEvent, status: TaskStatus): void {
    event.preventDefault();
    this.dragOverColumn = status;
  }

  clearKanbanDrop(status: TaskStatus): void {
    if (this.dragOverColumn === status) {
      this.dragOverColumn = null;
    }
  }

  dropTaskOnColumn(status: TaskStatus): void {
    if (this.draggedTaskId == null) {
      this.dragOverColumn = null;
      return;
    }

    const task = this.tasks.find((item) => item.id === this.draggedTaskId);
    this.draggedTaskId = null;
    this.dragOverColumn = null;

    if (!task || task.status === status) {
      return;
    }

    this.updateTask(task, { title: task.title, status }, 'Não foi possível atualizar o status da tarefa.');
  }

  isDragOverColumn(status: TaskStatus): boolean {
    return this.dragOverColumn === status;
  }

  isDraggingTask(taskId: number): boolean {
    return this.draggedTaskId === taskId;
  }

  private syncAuthModeValidators(): void {
    const nameControl = this.authForm.controls.name;

    if (this.authMode === 'register') {
      nameControl.setValidators([Validators.required, Validators.minLength(2)]);
    } else {
      nameControl.clearValidators();
    }

    nameControl.updateValueAndValidity({ emitEvent: false });
  }

  private updateTask(task: Task, payload: TaskRequest, errorMessage: string): void {
    this.taskService.update(task.id, payload).subscribe({
      next: () => {
        this.loadTasks();
      },
      error: () => {
        this.showNotice('error', errorMessage);
      }
    });
  }

  private buildTaskQuery(): TaskListQuery {
    return {
      search: this.filterState.search,
      statuses: this.filterState.statuses,
      sortBy: this.filterState.sortBy,
      sortDirection: this.filterState.sortDirection
    };
  }

  clearNotice(): void {
    if (this.noticeTimeoutId) {
      clearTimeout(this.noticeTimeoutId);
      this.noticeTimeoutId = null;
    }
    if (this.noticeIntervalId) {
      clearInterval(this.noticeIntervalId);
      this.noticeIntervalId = null;
    }
    this.notice = null;
    this.noticeCountdown = 0;
  }

  private showNotice(type: NoticeType, message: string): void {
    this.clearNotice();
    this.notice = { type, message };
    this.noticeCountdown = 5;
    this.noticeIntervalId = setInterval(() => {
      this.noticeCountdown = Math.max(0, this.noticeCountdown - 1);
    }, 1000);
    this.noticeTimeoutId = setTimeout(() => {
      this.clearNotice();
    }, 5000);
  }
}
