# Quickstart: Testes de Integração do Backend com Testcontainers

## Pré-requisito
- Docker disponível e funcional no ambiente local

## Execução
```bash
cd backend
mvn test
```

## Escopo esperado
- unit tests existentes
- testes de integração com PostgreSQL real via Testcontainers
- migrations Flyway aplicadas automaticamente no container
