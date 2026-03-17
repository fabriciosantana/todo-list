# Implementation Plan: Melhorar Cobertura de Testes

**Branch**: `006-improve-test-coverage` | **Date**: 2026-03-17 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/006-improve-test-coverage/spec.md`

## Summary

Aumentar a cobertura útil do projeto priorizando serviços Angular com efeitos colaterais e serviços Java ligados a autenticação/JWT, sem inflar cobertura com testes cosméticos.

## Technical Context

**Language/Version**: Java 21+, TypeScript/Angular 20  
**Primary Dependencies**: JUnit 5, Mockito, Spring Test, Angular TestBed, HttpClientTestingModule/HttpTestingController  
**Storage**: `localStorage` no frontend; mocks no backend  
**Testing**: `mvn test jacoco:report`, `npm run test:ci`  
**Target Platform**: Local Linux/Codespaces e GitHub Actions  
**Project Type**: Web app full-stack  
**Performance Goals**: manter testes rápidos e determinísticos  
**Constraints**: não depender de banco real nem browser interativo; focar regras críticas  
**Scale/Scope**: serviços e componente principal já existentes

## Constitution Check

- User Value First: OK, reduz risco de regressão nos fluxos principais.
- Object-Oriented Design: OK, reforça contratos de serviço e domínio.
- Clean Architecture by Layers: OK, testes respeitam fronteiras de camada.
- Multiuser Security by Default: OK, cobre autenticação/JWT.
- Quality and Operability: OK, objetivo central da iteração.

## Project Structure

```text
backend/src/test/java/com/todo/app/
frontend/src/app/
specs/006-improve-test-coverage/
```

**Structure Decision**: manter testes próximos aos módulos atuais e adicionar apenas artefatos spec-kit da iteração.
