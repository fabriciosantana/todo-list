import { of } from 'rxjs';
import { TestBed } from '@angular/core/testing';
import { App } from './app';
import { AuthService } from './core/auth.service';
import { TaskService } from './core/task.service';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [
        {
          provide: AuthService,
          useValue: {
            getUser: () => null,
            isAuthenticated: () => false,
            register: () => of(),
            login: () => of(),
            logout: () => undefined,
          },
        },
        {
          provide: TaskService,
          useValue: {
            list: () => of([]),
            create: () => of(),
            update: () => of(),
            archive: () => of(),
            unarchive: () => of(),
            delete: () => of(),
          },
        },
      ],
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
});
