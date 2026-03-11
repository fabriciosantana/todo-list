# Tasks: Status de Tarefas com Visão Tabela e Kanban

**Input**: Design documents from `/specs/004-task-status-kanban/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

## Phase 1: Setup

- [x] T001 Criar feature `004-task-status-kanban`
- [x] T002 Definir spec, plan, research, data-model, quickstart e contrato da feature

## Phase 2: Backend Foundation

- [x] T003 Criar enum `TaskStatus` em `backend/src/main/java/com/todo/app/domain/model/TaskStatus.java`
- [x] T004 Atualizar entidade `Task`, DTOs e `TaskService` para usar `status`
- [x] T005 Criar migration Flyway `backend/src/main/resources/db/migration/V2__add_task_status.sql`

## Phase 3: Frontend

- [x] T006 Atualizar modelos e serviços Angular para consumir `status`
- [x] T007 Implementar visão em tabela com coluna de status em `frontend/src/app/app.html`
- [x] T008 Implementar visão kanban e alternância de modo em `frontend/src/app/app.html`
- [x] T009 Ajustar estilos e interações do componente principal em `frontend/src/app/app.css` e `frontend/src/app/app.ts`

## Phase 4: Arquivamento

- [x] T010 Adicionar suporte de arquivamento no backend e migration Flyway
- [x] T011 Adicionar listagem e ações de arquivar/desarquivar no frontend

## Phase 5: UX e Feedback

- [x] T012 Adicionar drag and drop no kanban para mudança de status
- [x] T013 Restringir exclusão definitiva a tarefas arquivadas com confirmação explícita
- [x] T014 Implementar mensagens temporárias com contador e fechamento manual

## Phase 6: Validation

- [x] T015 Executar `mvn test` no backend
- [x] T016 Executar `npm run build` no frontend
