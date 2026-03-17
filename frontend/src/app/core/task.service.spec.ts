import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { TaskService } from './task.service';
import { Task } from '../models/types';

describe('TaskService', () => {
  let service: TaskService;
  let httpMock: HttpTestingController;

  const task: Task = {
    id: 7,
    title: 'Implementar cobertura',
    status: 'A_FAZER',
    archived: false,
    createdAt: '2026-03-17T10:00:00Z',
    updatedAt: '2026-03-17T10:00:00Z'
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });

    service = TestBed.inject(TaskService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should list active tasks by default', () => {
    service.list().subscribe();

    const request = httpMock.expectOne((req) =>
      req.url === 'http://localhost:8080/api/tasks' && req.params.get('archived') === 'false'
    );

    expect(request.request.method).toBe('GET');
    request.flush([task]);
  });

  it('should list archived tasks when requested', () => {
    service.list(true).subscribe();

    const request = httpMock.expectOne((req) =>
      req.url === 'http://localhost:8080/api/tasks' && req.params.get('archived') === 'true'
    );

    expect(request.request.method).toBe('GET');
    request.flush([task]);
  });

  it('should create a task', () => {
    service.create({ title: 'Nova tarefa', status: 'FAZENDO' }).subscribe();

    const request = httpMock.expectOne('http://localhost:8080/api/tasks');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual({ title: 'Nova tarefa', status: 'FAZENDO' });
    request.flush(task);
  });

  it('should update a task', () => {
    service.update(7, { title: 'Atualizada', status: 'CONCLUIDO' }).subscribe();

    const request = httpMock.expectOne('http://localhost:8080/api/tasks/7');
    expect(request.request.method).toBe('PUT');
    expect(request.request.body).toEqual({ title: 'Atualizada', status: 'CONCLUIDO' });
    request.flush({ ...task, title: 'Atualizada', status: 'CONCLUIDO' });
  });

  it('should toggle task status', () => {
    service.toggle(7).subscribe();

    const request = httpMock.expectOne('http://localhost:8080/api/tasks/7/toggle');
    expect(request.request.method).toBe('PATCH');
    expect(request.request.body).toEqual({});
    request.flush({ ...task, status: 'CONCLUIDO' });
  });

  it('should archive, unarchive and delete a task', () => {
    service.archive(7).subscribe();
    const archiveRequest = httpMock.expectOne('http://localhost:8080/api/tasks/7/archive');
    expect(archiveRequest.request.method).toBe('PATCH');
    archiveRequest.flush({ ...task, archived: true });

    service.unarchive(7).subscribe();
    const unarchiveRequest = httpMock.expectOne('http://localhost:8080/api/tasks/7/unarchive');
    expect(unarchiveRequest.request.method).toBe('PATCH');
    unarchiveRequest.flush(task);

    service.delete(7).subscribe();
    const deleteRequest = httpMock.expectOne('http://localhost:8080/api/tasks/7');
    expect(deleteRequest.request.method).toBe('DELETE');
    deleteRequest.flush(null);
  });
});
