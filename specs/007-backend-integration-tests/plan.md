# Implementation Plan: Testes de Integração do Backend com Testcontainers

**Branch**: `007-backend-integration-tests` | **Date**: 2026-03-17 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/007-backend-integration-tests/spec.md`

## Summary

Adicionar testes de integração do backend com PostgreSQL real via Testcontainers, cobrindo migrations, repositórios e fluxos HTTP principais de autenticação e tarefas.

## Technical Context

**Language/Version**: Java 21 / Spring Boot 3.4  
**Primary Dependencies**: Spring Boot Test, MockMvc, Testcontainers PostgreSQL, Flyway  
**Storage**: PostgreSQL efêmero em container  
**Testing**: `mvn test`, `mvn verify`  
**Target Platform**: Docker local/Codespaces e GitHub Actions Linux  
**Project Type**: backend Spring Boot  
**Performance Goals**: manter suíte de integração pequena e focada  
**Constraints**: depende de Docker disponível; deve reutilizar migrations reais  
**Scale/Scope**: autenticação, tarefas e persistência principal do backend

## Constitution Check

- User Value First: OK, cobre fluxos críticos com infraestrutura real.
- Object-Oriented Design: OK, valida camadas sem romper arquitetura.
- Clean Architecture by Layers: OK, testes de integração observam os contratos entre camadas.
- Multiuser Security by Default: OK, foco explícito em autenticação e isolamento por usuário.
- Quality and Operability: OK, eleva confiança do pipeline.

## Project Structure

```text
backend/
├── pom.xml
└── src/test/java/com/todo/app/
    ├── integration/
    └── repository/

specs/007-backend-integration-tests/
```

**Structure Decision**: manter unit tests existentes e adicionar suíte de integração separada por pacote.
