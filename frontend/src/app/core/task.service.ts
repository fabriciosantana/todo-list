import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task, TaskRequest } from '../models/types';
import { runtimeConfig } from '../config/runtime-config';

@Injectable({ providedIn: 'root' })
export class TaskService {
  private readonly apiBase = `${runtimeConfig.apiBaseUrl}/api/tasks`;

  constructor(private readonly http: HttpClient) {}

  list(archived = false): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiBase, {
      params: {
        archived: String(archived)
      }
    });
  }

  create(payload: TaskRequest): Observable<Task> {
    return this.http.post<Task>(this.apiBase, payload);
  }

  update(taskId: number, payload: TaskRequest): Observable<Task> {
    return this.http.put<Task>(`${this.apiBase}/${taskId}`, payload);
  }

  toggle(taskId: number): Observable<Task> {
    return this.http.patch<Task>(`${this.apiBase}/${taskId}/toggle`, {});
  }

  delete(taskId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiBase}/${taskId}`);
  }

  archive(taskId: number): Observable<Task> {
    return this.http.patch<Task>(`${this.apiBase}/${taskId}/archive`, {});
  }

  unarchive(taskId: number): Observable<Task> {
    return this.http.patch<Task>(`${this.apiBase}/${taskId}/unarchive`, {});
  }
}
