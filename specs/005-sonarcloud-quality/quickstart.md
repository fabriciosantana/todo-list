# Quickstart: Qualidade e Cobertura com SonarCloud

## Configuração no GitHub
1. Criar secret `SONAR_TOKEN` em `Settings > Secrets and variables > Actions`.
2. Criar variables `SONAR_ORGANIZATION` e `SONAR_PROJECT_KEY`.
3. Confirmar que o projeto já existe ou pode ser provisionado no SonarCloud.

## Validação local
1. Entrar em `backend/` e rodar `mvn test`.
2. Confirmar o arquivo `backend/target/site/jacoco/jacoco.xml`.
3. Entrar em `frontend/` e rodar `npm run test:ci`.
4. Confirmar o arquivo `frontend/coverage/frontend/lcov.info`.
5. Opcionalmente rodar o scanner local usando as mesmas variáveis do workflow.

## Validação no pipeline
1. Fazer push para a branch da feature.
2. Verificar execução do workflow `SonarCloud Quality`.
3. Confirmar no SonarCloud a atualização de cobertura, bugs, code smells e quality gate.
