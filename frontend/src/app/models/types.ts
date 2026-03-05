export interface User {
  id: number;
  name: string;
  email: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface Task {
  id: number;
  title: string;
  completed: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface TaskRequest {
  title: string;
  completed?: boolean;
}
