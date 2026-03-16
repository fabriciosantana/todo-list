# Contract: SonarCloud Quality Pipeline

## Inputs
- Secret obrigatório: `SONAR_TOKEN`
- Variable obrigatória: `SONAR_ORGANIZATION`
- Variable obrigatória: `SONAR_PROJECT_KEY`

## Generated artifacts
- `backend/target/site/jacoco/jacoco.xml`
- `frontend/coverage/frontend/lcov.info`

## Workflow behavior
- Executa testes backend com cobertura JaCoCo.
- Executa testes frontend em Chrome headless com LCOV.
- Executa scanner SonarCloud usando os relatórios gerados.
- Exclui `target`, `dist`, `coverage`, `node_modules` e arquivos gerados da análise de fontes.
