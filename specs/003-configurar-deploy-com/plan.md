# Implementation Plan: Deploy com GitHub Actions + Netlify + Render + Supabase

**Branch**: `003-configurar-deploy-com` | **Date**: 2026-03-05 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/003-configurar-deploy-com/spec.md`

## Summary

Implementar CI/CD com GitHub Actions para validar build full-stack e automatizar deploy do frontend no Netlify e backend no Render, usando Supabase como banco PostgreSQL via variáveis de ambiente.

## Technical Context

**Language/Version**: YAML (GitHub Actions), Java 21, Node 20  
**Primary Dependencies**: GitHub Actions, Netlify CLI, Render Deploy Hook  
**Storage**: Supabase PostgreSQL (externo)  
**Testing**: `mvn test` e `npm run build` em CI  
**Target Platform**: GitHub-hosted runners (`ubuntu-latest`)  
**Project Type**: Monorepo backend + frontend  
**Performance Goals**: pipeline previsível e reproduzível  
**Constraints**: segredos fora do código, deploy gatilhado por `main`  
**Scale/Scope**: deploy de produção inicial

## Constitution Check

- User Value First: OK, entrega automação de publicação.
- Object-Oriented Design: Mantido no backend.
- Clean Architecture by Layers: Mantido, sem impacto estrutural no domínio.
- Multiuser Security by Default: Mantido; deploy não remove proteção existente.
- Quality and Operability: OK, documentação e pipelines explícitos.

## Project Structure

### Documentation (this feature)

```text
specs/003-configurar-deploy-com/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
└── tasks.md
```

### Source Code (repository root)

```text
.github/workflows/
├── ci.yml
├── deploy-frontend-netlify.yml
└── deploy-backend-render.yml

netlify.toml
README.md
```

**Structure Decision**: centralizar automações em workflows separados por responsabilidade (CI, deploy frontend, deploy backend).

## Complexity Tracking

Sem violações de constituição previstas.
