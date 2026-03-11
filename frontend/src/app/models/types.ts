export interface User {
  id: number;
  name: string;
  email: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export type TaskStatus = 'A_FAZER' | 'FAZENDO' | 'CONCLUIDO';

export interface Task {
  id: number;
  title: string;
  status: TaskStatus;
  archived: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface TaskRequest {
  title: string;
  status?: TaskStatus;
}
