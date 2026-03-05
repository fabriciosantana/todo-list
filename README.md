# Todo List Multiusuário (Spring Boot + Angular + PostgreSQL)

Aplicação migrada para arquitetura full-stack usando:

- Backend: Java + Spring Boot + Spring Security + JPA
- Frontend: Angular + Bootstrap
- Banco: PostgreSQL
- Autenticação: JWT

## Estrutura

- `backend/`: API REST e regras de domínio orientadas a objetos
- `frontend/`: SPA Angular
- `specs/002-migrar-todo-list/`: documentação spec-kit da migração

## Como rodar

### 1) Banco de dados

```bash
docker compose up -d postgres
```

### 2) Backend

```bash
cd backend
mvn spring-boot:run
```

API disponível em `http://localhost:8080`.
Health check disponível em `http://localhost:8080/api/health`.

Se necessário, ajuste CORS via variável de ambiente:

```bash
export CORS_ALLOWED_ORIGIN_PATTERNS="http://localhost:4200,http://127.0.0.1:4200,https://SEU-DOMINIO.app.github.dev"
```

### 3) Frontend

```bash
cd frontend
npm install
npm start
```

Aplicação disponível em `http://localhost:4200`.

## Fluxo de uso

1. Cadastrar usuário.
2. Fazer login.
3. Criar, editar, concluir e remover tarefas.
4. Fazer logout e logar com outra conta para validar isolamento multiusuário.
