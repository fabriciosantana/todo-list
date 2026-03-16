# Implementation Plan: Qualidade e Cobertura com SonarCloud

**Branch**: `005-sonarcloud-quality` | **Date**: 2026-03-16 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/005-sonarcloud-quality/spec.md`

## Summary

Configurar o repositório para gerar cobertura de backend e frontend, executar scanner do SonarCloud no GitHub Actions e documentar a operação completa de quality gate.

## Technical Context

**Language/Version**: Java 21+, TypeScript/Angular 20  
**Primary Dependencies**: Maven Surefire, JaCoCo, Angular/Karma, SonarScanner for GitHub Actions  
**Storage**: N/A  
**Testing**: `mvn test`, `npm test -- --watch=false --browsers ChromeHeadless --code-coverage`  
**Target Platform**: GitHub Actions Ubuntu + execução local Linux/Codespaces  
**Project Type**: Web app full-stack com pipeline CI/CD  
**Performance Goals**: manter a análise de qualidade dentro de um job único e previsível  
**Constraints**: credenciais via secrets, exclusão de artefatos gerados, cobertura separada por stack  
**Scale/Scope**: análise estática e cobertura do monorepo atual

## Constitution Check

- User Value First: OK, melhora sustentabilidade do produto.
- Object-Oriented Design: OK, sem ruptura de domínio.
- Clean Architecture by Layers: OK, muda tooling e testes, não a arquitetura.
- Multiuser Security by Default: OK, sem impacto na camada de segurança da aplicação.
- Quality and Operability: OK, reforça explicitamente este princípio.

## Project Structure

```text
backend/
├── pom.xml
└── src/test/

frontend/
├── package.json
├── src/app/
└── tsconfig.spec.json

.github/workflows/
sonarcloud.yml

sonar-project.properties
specs/005-sonarcloud-quality/
```

**Structure Decision**: manter a estrutura atual e adicionar configuração de qualidade no root, cobertura no backend/frontend e workflow dedicado.
