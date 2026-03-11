# Feature Specification: Migração Todo-List para Java + Angular

**Feature Branch**: `002-migrar-todo-list`  
**Created**: 2026-03-05  
**Status**: Draft  
**Input**: User description: "no branch develop, usando o spec-kit, faça a migração da aplicação para java usando o paradgima orientada a objetos, spring boot, angular, bootstrap e banco de dados postgres"

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Cadastro e login multiusuário (Priority: P1)

Como usuário, quero criar conta e autenticar para acessar apenas minhas próprias tarefas.

**Why this priority**: Sem autenticação multiusuário, o requisito principal de isolamento de dados não é atendido.

**Independent Test**: Registrar usuário, fazer login e consumir endpoint protegido com token.

**Acceptance Scenarios**:

1. **Given** um e-mail novo, **When** envio cadastro válido, **Then** recebo token e dados do usuário.
2. **Given** credenciais válidas, **When** envio login, **Then** recebo token JWT utilizável nos endpoints de tarefa.

---

### User Story 2 - CRUD de tarefas por usuário (Priority: P1)

Como usuário autenticado, quero criar, editar, concluir e remover tarefas para organizar meu trabalho.

**Why this priority**: É o núcleo funcional do produto.

**Independent Test**: Executar todas operações de tarefa usando token do usuário.

**Acceptance Scenarios**:

1. **Given** usuário autenticado, **When** cria tarefa, **Then** tarefa aparece na listagem do próprio usuário.
2. **Given** tarefa existente do usuário, **When** atualiza status/título, **Then** alteração persiste no banco.
3. **Given** tarefa existente do usuário, **When** remove tarefa, **Then** ela não aparece mais na listagem.

---

### User Story 3 - Interface Angular com Bootstrap (Priority: P2)

Como usuário, quero uma interface web Angular responsiva para usar login e gerenciar tarefas.

**Why this priority**: Necessário para experiência final de uso.

**Independent Test**: Abrir frontend, autenticar e operar tarefas sem usar ferramentas externas.

**Acceptance Scenarios**:

1. **Given** usuário não autenticado, **When** abre aplicação, **Then** vê tela de login/cadastro.
2. **Given** usuário autenticado, **When** interage com a tela principal, **Then** consegue operar CRUD de tarefas com feedback visual.

---

### Edge Cases

- Cadastro com e-mail já existente deve retornar erro controlado.
- Requisição sem token deve ser negada com `401`.
- Usuário não pode atualizar/remover tarefa de outro usuário.
- Título de tarefa vazio deve ser rejeitado.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: Sistema MUST permitir registro de usuário com nome, e-mail e senha.
- **FR-002**: Sistema MUST autenticar usuário via login e retornar token JWT.
- **FR-003**: Sistema MUST proteger endpoints de tarefa com autenticação.
- **FR-004**: Sistema MUST garantir isolamento por usuário em todas as operações de tarefa.
- **FR-005**: Sistema MUST persistir usuários e tarefas no PostgreSQL.
- **FR-006**: Frontend Angular MUST oferecer fluxo de login/cadastro.
- **FR-007**: Frontend Angular MUST permitir CRUD completo de tarefas.
- **FR-008**: Frontend MUST usar Bootstrap para layout e componentes visuais.

### Key Entities *(include if feature involves data)*

- **User**: conta da aplicação com `id`, `name`, `email`, `passwordHash`, `role`.
- **Task**: tarefa vinculada a um usuário com `id`, `title`, `completed`, `createdAt`, `updatedAt`, `owner`.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Usuário consegue registrar e logar em menos de 1 minuto.
- **SC-002**: 100% das operações de tarefa exigem token válido.
- **SC-003**: Usuário A não consegue ler nem alterar tarefas do usuário B.
- **SC-004**: Fluxo completo frontend (auth + CRUD) funciona sem erros críticos no console.
