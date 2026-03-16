# Tasks: Qualidade e Cobertura com SonarCloud

**Input**: Design documents from `/specs/005-sonarcloud-quality/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

## Phase 1: Setup
- [x] T001 Criar feature `005-sonarcloud-quality`
- [x] T002 Definir spec, plan, research, data-model, quickstart e contrato da integração

## Phase 2: Cobertura
- [ ] T003 Configurar JaCoCo em `backend/pom.xml`
- [ ] T004 Criar/ajustar testes backend para produzir cobertura relevante
- [ ] T005 Ajustar testes Angular para execução headless com cobertura LCOV

## Phase 3: Integração SonarCloud
- [ ] T006 Criar `sonar-project.properties` com fontes, exclusões e caminhos de coverage
- [ ] T007 Criar workflow `.github/workflows/sonarcloud.yml`
- [ ] T008 Atualizar README com setup de SonarCloud, variables e fluxo operacional

## Phase 4: Validation
- [ ] T009 Validar `mvn test`
- [ ] T010 Validar `npm test -- --watch=false --browsers ChromeHeadless --code-coverage`
