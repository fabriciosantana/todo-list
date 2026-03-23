# Implementation Plan: Filtro, Busca e Ordenação na Lista de Tarefas

**Branch**: `010-task-list-search-filter-sort` | **Date**: 2026-03-20 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/010-task-list-search-filter-sort/spec.md`

## Summary

Adicionar busca textual, filtro por status e ordenação configurável nas listagens de tarefas, com suporte coerente no backend e nas visões tabela/kanban do frontend.

## Technical Context

**Language/Version**: Java 21 / Spring Boot 3.4, TypeScript / Angular 20  
**Primary Dependencies**: Spring Data JPA, Angular Reactive Forms, HttpClient  
**Storage**: PostgreSQL  
**Testing**: `mvn test`, `npm run test:ci`, `npx playwright test` se necessário  
**Target Platform**: Browser, Render, Netlify  
**Project Type**: aplicação full-stack com backend Spring e frontend Angular  
**Performance Goals**: listagens filtradas sem duplicar processamento pesado no cliente  
**Constraints**: manter compatibilidade com escopos ativo/arquivado e com a visão kanban  
**Scale/Scope**: listagem individual por usuário autenticado

## Constitution Check

- User Value First: OK, melhora a usabilidade central da lista de tarefas.
- Object-Oriented Design: OK, o backend encapsula os critérios de busca na camada de serviço/repositório.
- Clean Architecture by Layers: OK, filtro e ordenação entram pela API e são refletidos na UI sem violar separação de camadas.
- Multiuser Security by Default: OK, a consulta continua restrita ao `ownerId` autenticado.
- Quality and Operability: OK, testes precisam cobrir combinações principais de query e renderização.

## Project Structure

```text
backend/
├── src/main/java/com/todo/app/
│   ├── application/service/
│   ├── domain/repository/
│   └── web/controller/

frontend/
├── src/app/
│   ├── app.ts
│   ├── app.html
│   └── core/task.service.ts

specs/010-task-list-search-filter-sort/
```

**Structure Decision**: implementar query params no backend, refletir controles no componente principal do frontend e documentar a iteração no spec-kit.
