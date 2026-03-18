# Tasks: Observabilidade com Grafana Cloud e LGTM local

**Input**: Design documents from `/specs/009-observability-grafana-cloud/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

## Phase 1: Setup
- [x] T001 Criar feature `009-observability-grafana-cloud`
- [x] T002 Definir spec, plan, research, data-model, quickstart e contrato da iteração

## Phase 2: Backend Instrumentation
- [x] T003 Adicionar dependências de observabilidade ao backend
- [x] T004 Configurar Actuator, métricas, traces e logs estruturados
- [x] T005 Configurar exportação opcional para Grafana Cloud

## Phase 3: Local Stack
- [x] T006 Criar stack LGTM local via Docker Compose
- [x] T007 Provisionar Grafana e Prometheus locais

## Phase 4: Validation
- [x] T008 Validar startup e smoke checks do backend instrumentado
- [x] T009 Atualizar documentação operacional do projeto
