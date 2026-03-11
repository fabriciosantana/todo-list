# Implementation Plan: Status de Tarefas com Visão Tabela e Kanban

**Branch**: `004-task-status-kanban` | **Date**: 2026-03-11 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/004-task-status-kanban/spec.md`

## Summary

Introduzir status explícito nas tarefas, migrando o backend de `completed:boolean` para `status:enum`, e evoluir o frontend Angular para oferecer duas visões da mesma lista: tabela e kanban.

## Technical Context

**Language/Version**: Java 21+, TypeScript/Angular 20  
**Primary Dependencies**: Spring Boot, Spring Data JPA, Spring Security, Flyway, Angular, Bootstrap  
**Storage**: PostgreSQL  
**Testing**: `mvn test` no backend e `npm run build` no frontend  
**Target Platform**: Linux server + navegador moderno  
**Project Type**: Web app full-stack  
**Performance Goals**: atualização de status sem reload completo da página  
**Constraints**: multiusuário, JWT stateless, migração sem perda de dados legados  
**Scale/Scope**: evolução incremental da gestão de tarefas existente

## Constitution Check

- User Value First: OK, a feature melhora rastreamento de trabalho e visualização.
- Object-Oriented Design: OK, novo enum de domínio e ajustes explícitos em entidade/serviço.
- Clean Architecture by Layers: OK, mudança distribuída entre `domain`, `application`, `web` e frontend.
- Multiuser Security by Default: OK, escopo por owner permanece inalterado.
- Quality and Operability: OK, Flyway garante atualização automática do schema.

## Project Structure

### Documentation (this feature)

```text
specs/004-task-status-kanban/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
└── tasks.md
```

### Source Code (repository root)

```text
backend/
├── src/main/java/com/todo/app/
├── src/main/resources/db/migration/
└── pom.xml

frontend/
├── src/app/
└── src/styles.css
```

**Structure Decision**: manter a estrutura atual de backend e frontend, com evolução pontual do modelo de tarefas, contrato REST e tela principal Angular.

## Complexity Tracking

Sem violações previstas da constituição.
