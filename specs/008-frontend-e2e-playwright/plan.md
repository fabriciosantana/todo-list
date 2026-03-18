# Implementation Plan: Testes E2E do Frontend com Playwright

**Branch**: `008-frontend-e2e-playwright` | **Date**: 2026-03-17 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/008-frontend-e2e-playwright/spec.md`

## Summary

Adicionar Playwright ao projeto para executar testes E2E do frontend com backend e banco reais, compatíveis com Codespaces e cobrindo autenticação e fluxo principal de tarefas.

## Technical Context

**Language/Version**: TypeScript / Playwright, Angular 20, Java 21 / Spring Boot 3.4  
**Primary Dependencies**: `@playwright/test`, Angular CLI, Spring Boot, Docker Compose  
**Storage**: PostgreSQL local via `docker compose`  
**Testing**: `npx playwright test`, browsers gerenciados pelo Playwright  
**Target Platform**: GitHub Codespaces e GitHub Actions Linux  
**Project Type**: full-stack monorepo (frontend + backend)  
**Performance Goals**: suíte pequena, cobrindo fluxos críticos em poucos minutos  
**Constraints**: não depender de browser do sistema; backend e frontend devem subir automaticamente  
**Scale/Scope**: autenticação, tabela, kanban, arquivamento, exclusão

## Constitution Check

- User Value First: OK, cobre os fluxos reais que o usuário executa.
- Object-Oriented Design: OK, exercita a aplicação sem romper os contratos das camadas.
- Clean Architecture by Layers: OK, valida a integração real entre frontend, backend e banco.
- Multiuser Security by Default: OK, autenticação e isolamento por usuário fazem parte do escopo.
- Quality and Operability: OK, amplia a malha de testes para o cenário mais próximo de produção.

## Project Structure

```text
frontend/
├── e2e/
├── package.json
└── playwright.config.ts

specs/008-frontend-e2e-playwright/
```

**Structure Decision**: manter os testes E2E junto do frontend, usando Playwright com `webServer` para subir backend e frontend automaticamente.
