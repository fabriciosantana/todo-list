# Tasks: Migração Todo-List para Java + Angular

**Input**: Design documents from `/specs/002-migrar-todo-list/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

## Phase 1: Setup

- [x] T001 Criar feature `002-migrar-todo-list`
- [x] T002 Atualizar constituição para stack Java/Angular/PostgreSQL
- [x] T003 Definir spec, plan e contratos da migração

## Phase 2: Backend Foundation (Spring Boot)

- [x] T004 Criar projeto `backend/` com dependências Spring Boot
- [x] T005 Implementar entidades OOP `User` e `Task`
- [x] T006 Implementar repositórios JPA e serviços de domínio
- [x] T007 Implementar segurança JWT e autenticação multiusuário

## Phase 3: Backend API

- [x] T008 Implementar endpoints de autenticação (`/api/auth/*`)
- [x] T009 Implementar CRUD de tarefas em `/api/tasks`
- [x] T010 Garantir isolamento por usuário em todas as operações

## Phase 4: Frontend (Angular + Bootstrap)

- [x] T011 Configurar Bootstrap no projeto Angular
- [x] T012 Implementar fluxo de cadastro/login
- [x] T013 Implementar tela de tarefas com CRUD
- [x] T014 Integrar token JWT via interceptor HTTP

## Phase 5: Operação e Documentação

- [x] T015 Criar `docker-compose.yml` para PostgreSQL
- [x] T016 Atualizar README com instruções completas
- [x] T017 Validar build backend e frontend
