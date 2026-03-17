# Tasks: Melhorar Cobertura de Testes

**Input**: Design documents from `/specs/006-improve-test-coverage/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

## Phase 1: Setup
- [x] T001 Criar feature `006-improve-test-coverage`
- [x] T002 Definir spec, plan, research, data-model, quickstart e contrato da iteração

## Phase 2: Frontend Coverage
- [x] T003 Criar testes unitários para `auth.service.ts`
- [x] T004 Criar testes unitários para `task.service.ts`
- [x] T005 Ampliar testes do componente principal `app.ts`

## Phase 3: Backend Coverage
- [x] T006 Criar testes unitários para `AuthService`
- [x] T007 Criar testes unitários para `JwtService`
- [x] T008 Criar testes unitários para `AppUserDetailsService`

## Phase 4: Validation
- [x] T009 Validar `mvn test jacoco:report`
- [x] T010 Validar `npm run test:ci`
