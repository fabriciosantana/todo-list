# Todo List Constitution

## Core Principles

### I. User Value First
Cada incremento deve melhorar diretamente a experiência de gerenciamento de tarefas do usuário.

### II. Object-Oriented Design
O backend deve adotar modelagem orientada a objetos com entidades claras, encapsulamento e separação de responsabilidades.

### III. Clean Architecture by Layers
A solução deve manter fronteiras explícitas entre camadas (`web`, `application`, `domain`, `config`) para reduzir acoplamento e facilitar manutenção.

### IV. Multiuser Security by Default
Toda operação de tarefa deve ser autenticada e isolada por usuário, impedindo acesso cruzado entre contas.

### V. Quality and Operability
A aplicação deve ser simples de executar localmente (backend, frontend e PostgreSQL) com documentação objetiva de setup e validação.

## Technical Constraints

- Backend: Java + Spring Boot + Spring Security + Spring Data JPA.
- Frontend: Angular + Bootstrap.
- Banco de dados: PostgreSQL.
- Comunicação: API REST JSON.
- Persistência de sessão: token JWT no cliente.

## Workflow and Quality Gates

- Toda feature inicia em `specs/<feature>/spec.md`.
- `plan.md` define contexto técnico, estrutura e riscos.
- `tasks.md` organiza execução por histórias de usuário.
- Entrega só é concluída após validação dos fluxos descritos em `quickstart.md`.

## Governance

Esta constituição prevalece sobre práticas ad-hoc no repositório. Mudanças estruturais devem atualizar este documento e versionamento.

**Version**: 2.0.0 | **Ratified**: 2026-03-05 | **Last Amended**: 2026-03-05
