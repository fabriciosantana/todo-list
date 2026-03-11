# Feature Specification: Aplicação Web Todo-List

**Feature Branch**: `001-aplica-web-todo`  
**Created**: 2026-03-05  
**Status**: Draft  
**Input**: User description: "Me ajude a desenvolver uma aplicação web usando o spec-kit. Quero desenvolver um todo-list para gerenciar tarefas."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Criar e concluir tarefas (Priority: P1)

Como usuário, quero cadastrar tarefas e marcá-las como concluídas para acompanhar o que já foi feito.

**Why this priority**: Este é o núcleo da proposta de valor de um todo-list.

**Independent Test**: Criar uma tarefa, marcar como concluída e recarregar a página para confirmar persistência.

**Acceptance Scenarios**:

1. **Given** que estou na tela inicial, **When** adiciono uma tarefa com título válido, **Then** ela aparece na lista como pendente.
2. **Given** que existe uma tarefa pendente, **When** marco como concluída, **Then** o status muda para concluída e permanece após recarregar.

---

### User Story 2 - Editar e remover tarefas (Priority: P2)

Como usuário, quero editar ou remover tarefas para manter a lista correta.

**Why this priority**: Complementa o fluxo principal com manutenção de dados.

**Independent Test**: Editar o título de uma tarefa e remover outra, validando atualização imediata na lista.

**Acceptance Scenarios**:

1. **Given** que existe uma tarefa, **When** edito o título, **Then** o novo texto é exibido e persistido.
2. **Given** que existe uma tarefa, **When** removo a tarefa, **Then** ela deixa de aparecer na lista.

---

### User Story 3 - Filtrar tarefas por status (Priority: P3)

Como usuário, quero filtrar tarefas (todas, pendentes, concluídas) para focar no que preciso fazer.

**Why this priority**: Melhora usabilidade, sem bloquear o MVP.

**Independent Test**: Criar tarefas com diferentes estados e alternar filtros.

**Acceptance Scenarios**:

1. **Given** tarefas pendentes e concluídas, **When** seleciono filtro "Pendentes", **Then** apenas pendentes são exibidas.

---

### Edge Cases

- Tentativa de criar tarefa com texto vazio ou apenas espaços.
- Edição com texto vazio deve ser bloqueada.
- Lista vazia deve exibir estado sem tarefas.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: Sistema MUST permitir adicionar tarefa com título.
- **FR-002**: Sistema MUST permitir marcar/desmarcar tarefa como concluída.
- **FR-003**: Sistema MUST persistir tarefas no `localStorage`.
- **FR-004**: Sistema MUST permitir editar título de uma tarefa.
- **FR-005**: Sistema MUST permitir remover tarefa.
- **FR-006**: Sistema MUST permitir filtrar tarefas por status: todas, pendentes, concluídas.
- **FR-007**: Sistema MUST mostrar feedback visual quando a lista estiver vazia.

### Key Entities *(include if feature involves data)*

- **Task**: item de tarefa com `id`, `title`, `completed`, `createdAt`, `updatedAt`.
- **FilterState**: estado atual do filtro de visualização (`all`, `active`, `completed`).

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Usuário consegue criar e concluir uma tarefa em até 30 segundos.
- **SC-002**: 100% das tarefas permanecem após recarregar a página no mesmo navegador.
- **SC-003**: Filtros exibem corretamente somente tarefas esperadas em todos os estados.
- **SC-004**: Fluxo principal (criar, concluir, editar, remover) funciona sem erros no console durante validação manual.
