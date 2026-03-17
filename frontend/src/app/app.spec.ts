import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { App } from './app';
import { AuthService } from './core/auth.service';
import { TaskService } from './core/task.service';
import { AuthResponse, Task, User } from './models/types';

describe('App', () => {
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let taskServiceSpy: jasmine.SpyObj<TaskService>;

  const user: User = {
    id: 1,
    name: 'Fabricio',
    email: 'fabricio@example.com'
  };

  const authResponse: AuthResponse = {
    token: 'token',
    user
  };

  const activeTask: Task = {
    id: 1,
    title: 'Cobrir serviços',
    status: 'A_FAZER',
    archived: false,
    createdAt: '2026-03-17T10:00:00Z',
    updatedAt: '2026-03-17T10:00:00Z'
  };

  const archivedTask: Task = {
    id: 2,
    title: 'Tarefa arquivada',
    status: 'CONCLUIDO',
    archived: true,
    createdAt: '2026-03-17T09:00:00Z',
    updatedAt: '2026-03-17T09:00:00Z'
  };

  beforeEach(async () => {
    authServiceSpy = jasmine.createSpyObj<AuthService>('AuthService', [
      'getUser',
      'isAuthenticated',
      'register',
      'login',
      'logout'
    ]);
    taskServiceSpy = jasmine.createSpyObj<TaskService>('TaskService', [
      'list',
      'create',
      'update',
      'archive',
      'unarchive',
      'delete'
    ]);

    authServiceSpy.getUser.and.returnValue(null);
    authServiceSpy.isAuthenticated.and.returnValue(false);
    authServiceSpy.register.and.returnValue(of(authResponse));
    authServiceSpy.login.and.returnValue(of(authResponse));
    taskServiceSpy.list.and.callFake((archived?: boolean) => of(archived ? [archivedTask] : [activeTask]));
    taskServiceSpy.create.and.returnValue(of(activeTask));
    taskServiceSpy.update.and.returnValue(of(activeTask));
    taskServiceSpy.archive.and.returnValue(of(archivedTask));
    taskServiceSpy.unarchive.and.returnValue(of(activeTask));
    taskServiceSpy.delete.and.returnValue(of(void 0));

    await TestBed.configureTestingModule({
      imports: [App],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        { provide: TaskService, useValue: taskServiceSpy }
      ]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render the application title', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Lista de Tarefas');
  });

  it('should load tasks on init when user is authenticated', () => {
    authServiceSpy.getUser.and.returnValue(user);
    authServiceSpy.isAuthenticated.and.returnValue(true);

    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;

    fixture.detectChanges();

    expect(app.currentUser).toEqual(user);
    expect(taskServiceSpy.list).toHaveBeenCalledWith(false);
    expect(taskServiceSpy.list).toHaveBeenCalledWith(true);
    expect(app.tasks).toEqual([activeTask]);
    expect(app.archivedTasks).toEqual([archivedTask]);
  });

  it('should register a user and load tasks', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    spyOn(app, 'loadTasks');
    app.authMode = 'register';
    app.authForm.setValue({
      name: 'Fabricio',
      email: 'fabricio@example.com',
      password: '123456'
    });

    app.submitAuth();

    expect(authServiceSpy.register).toHaveBeenCalledWith({
      name: 'Fabricio',
      email: 'fabricio@example.com',
      password: '123456'
    });
    expect(app.currentUser).toEqual(user);
    expect(app.notice).toEqual({ type: 'success', message: 'Cadastro realizado com sucesso.' });
    expect(app.loadTasks).toHaveBeenCalled();
    expect(app.loading).toBeFalse();
  });

  it('should show an error notice when login fails', () => {
    authServiceSpy.login.and.returnValue(throwError(() => ({ error: { message: 'Credenciais inválidas' } })));
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    app.authForm.patchValue({
      email: 'fabricio@example.com',
      password: '123456'
    });

    app.submitAuth();

    expect(authServiceSpy.login).toHaveBeenCalled();
    expect(app.notice).toEqual({ type: 'error', message: 'Credenciais inválidas' });
    expect(app.loading).toBeFalse();
  });

  it('should create a task and prepend it to the active list', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    app.tasks = [{ ...activeTask, id: 9, title: 'Existente' }];
    app.taskForm.setValue({ title: 'Cobrir serviços', status: 'A_FAZER' });

    app.createTask();

    expect(taskServiceSpy.create).toHaveBeenCalledWith({ title: 'Cobrir serviços', status: 'A_FAZER' });
    expect(app.tasks[0]).toEqual(activeTask);
    expect(app.taskForm.value.title).toBe('');
    expect(app.taskForm.value.status).toBe('A_FAZER');
  });

  it('should move task to archived list when archiving', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    app.tasks = [activeTask];
    app.archivedTasks = [];

    app.archiveTask(activeTask.id);

    expect(taskServiceSpy.archive).toHaveBeenCalledWith(activeTask.id);
    expect(app.tasks).toEqual([]);
    expect(app.archivedTasks).toEqual([archivedTask]);
    expect(app.notice).toEqual({ type: 'success', message: 'Tarefa arquivada.' });
  });

  it('should require archived task before deletion', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;

    app.deleteTask(activeTask);

    expect(taskServiceSpy.delete).not.toHaveBeenCalled();
    expect(app.notice).toEqual({
      type: 'success',
      message: 'Arquive a tarefa antes de excluí-la definitivamente.'
    });
  });

  it('should delete archived task after confirmation', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    app.tasks = [activeTask];
    app.archivedTasks = [archivedTask];
    spyOn(window, 'confirm').and.returnValue(true);

    app.deleteTask(archivedTask);

    expect(taskServiceSpy.delete).toHaveBeenCalledWith(archivedTask.id);
    expect(app.archivedTasks).toEqual([]);
    expect(app.notice).toEqual({ type: 'success', message: 'Tarefa removida permanentemente.' });
  });

  it('should update status via drag and drop when target column changes', () => {
    const updatedTask: Task = { ...activeTask, status: 'FAZENDO' };
    taskServiceSpy.update.and.returnValue(of(updatedTask));
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    app.tasks = [activeTask];

    app.startTaskDrag(activeTask);
    app.dropTaskOnColumn('FAZENDO');

    expect(taskServiceSpy.update).toHaveBeenCalledWith(activeTask.id, {
      title: activeTask.title,
      status: 'FAZENDO'
    });
    expect(app.tasks).toEqual([updatedTask]);
    expect(app.draggedTaskId).toBeNull();
    expect(app.dragOverColumn).toBeNull();
  });
});
