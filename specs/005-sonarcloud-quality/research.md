# Research: Qualidade e Cobertura com SonarCloud

## Decisão 1: usar workflow dedicado no GitHub Actions
- **Decisão**: criar um workflow `sonarcloud.yml` separado do `ci.yml` atual.
- **Racional**: mantém a análise de qualidade isolada do build básico e facilita troubleshooting de scanner/secrets.
- **Alternativas consideradas**:
  - Embutir tudo no `ci.yml`: rejeitado por misturar responsabilidades e aumentar ruído operacional.

## Decisão 2: JaCoCo para cobertura do backend
- **Decisão**: configurar o plugin JaCoCo no Maven para gerar `jacoco.xml` em `verify`/`test`.
- **Racional**: é o formato padrão aceito pelo ecossistema Java/Sonar.
- **Alternativas consideradas**:
  - Cobertura só com surefire sem XML: rejeitado porque o SonarCloud precisa de relatório consumível.

## Decisão 3: Karma headless com LCOV no frontend
- **Decisão**: usar `ng test` com `ChromeHeadless`, `--watch=false` e `--code-coverage`.
- **Racional**: aproveita a stack Angular já presente e gera `lcov.info`, formato esperado pelo SonarCloud para TS.
- **Alternativas consideradas**:
  - Migrar para outro runner nesta iteração: rejeitado por aumentar escopo e risco.
