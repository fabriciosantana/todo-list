import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { AuthResponse } from '../models/types';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  const authResponse: AuthResponse = {
    token: 'jwt-token',
    user: {
      id: 1,
      name: 'Fabricio',
      email: 'fabricio@example.com'
    }
  };

  beforeEach(() => {
    localStorage.clear();
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should persist session on register', () => {
    let responseBody: AuthResponse | undefined;

    service.register({ name: 'Fabricio', email: 'fabricio@example.com', password: '123456' }).subscribe({
      next: (response) => {
        responseBody = response;
      }
    });

    const request = httpMock.expectOne('http://localhost:8080/api/auth/register');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual({
      name: 'Fabricio',
      email: 'fabricio@example.com',
      password: '123456'
    });

    request.flush(authResponse);

    expect(responseBody).toEqual(authResponse);
    expect(localStorage.getItem('todo.jwt')).toBe('jwt-token');
    expect(localStorage.getItem('todo.user')).toBe(JSON.stringify(authResponse.user));
  });

  it('should persist session on login', () => {
    service.login({ email: 'fabricio@example.com', password: '123456' }).subscribe();

    const request = httpMock.expectOne('http://localhost:8080/api/auth/login');
    expect(request.request.method).toBe('POST');
    request.flush(authResponse);

    expect(service.isAuthenticated()).toBeTrue();
    expect(service.getToken()).toBe('jwt-token');
    expect(service.getUser()).toEqual(authResponse.user);
  });

  it('should clear persisted session on logout', () => {
    localStorage.setItem('todo.jwt', 'token');
    localStorage.setItem('todo.user', JSON.stringify(authResponse.user));

    service.logout();

    expect(service.getToken()).toBeNull();
    expect(service.getUser()).toBeNull();
    expect(service.isAuthenticated()).toBeFalse();
  });

  it('should return null when persisted user json is invalid', () => {
    localStorage.setItem('todo.user', '{invalid-json');

    expect(service.getUser()).toBeNull();
  });
});
