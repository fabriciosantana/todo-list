# Tasks: Deploy com GitHub Actions + Netlify + Render + Supabase

**Input**: Design documents from `/specs/003-configurar-deploy-com/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

## Phase 1: Setup

- [x] T001 Criar feature `003-configurar-deploy-com`
- [x] T002 Definir spec/plan/research/data-model/quickstart da iteração

## Phase 2: CI

- [x] T003 Criar workflow de CI para backend e frontend em `.github/workflows/ci.yml`

## Phase 3: Deploy Frontend

- [x] T004 Criar workflow de deploy Netlify em `.github/workflows/deploy-frontend-netlify.yml`
- [x] T005 Criar configuração `netlify.toml`

## Phase 4: Deploy Backend

- [x] T006 Criar workflow de deploy Render em `.github/workflows/deploy-backend-render.yml`
- [x] T007 Garantir compatibilidade com Supabase via variáveis no backend

## Phase 5: Documentation

- [x] T008 Atualizar `README.md` com instruções de CI/CD
- [x] T009 Validar consistência dos artefatos de deploy
