# Tasks: Testes de Integração do Backend com Testcontainers

**Input**: Design documents from `/specs/007-backend-integration-tests/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

## Phase 1: Setup
- [x] T001 Criar feature `007-backend-integration-tests`
- [x] T002 Definir spec, plan, research, data-model, quickstart e contrato da iteração

## Phase 2: Testcontainers Base
- [x] T003 Adicionar dependências Testcontainers em `backend/pom.xml`
- [x] T004 Criar infraestrutura base compartilhada para PostgreSQL em testes

## Phase 3: Integration Tests
- [x] T005 Criar teste de integração de repositório com PostgreSQL real
- [x] T006 Criar teste HTTP de autenticação com `MockMvc`
- [x] T007 Criar teste HTTP de tarefas autenticadas com `MockMvc`

## Phase 4: Validation
- [x] T008 Validar `mvn test`
- [x] T009 Ajustar documentação operacional se necessário
