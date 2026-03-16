# Data Model: Qualidade e Cobertura com SonarCloud

## SonarConfiguration
- **Descrição**: conjunto lógico de parâmetros de análise.
- **Campos**:
  - `organization`
  - `projectKey`
  - `token`
  - `sourcePaths`
  - `coveragePaths`

## BackendCoverageReport
- **Descrição**: relatório XML gerado pelo JaCoCo.
- **Path**: `backend/target/site/jacoco/jacoco.xml`

## FrontendCoverageReport
- **Descrição**: relatório LCOV gerado pelos testes Angular.
- **Path**: `frontend/coverage/frontend/lcov.info`

## QualityWorkflow
- **Descrição**: job GitHub Actions que orquestra testes, cobertura e scanner.
- **Entradas**:
  - `SONAR_TOKEN`
  - `SONAR_ORGANIZATION`
  - `SONAR_PROJECT_KEY`
