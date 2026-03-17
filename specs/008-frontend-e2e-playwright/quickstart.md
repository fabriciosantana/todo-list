# Quickstart: Testes E2E do Frontend com Playwright

## Pré-requisitos
- Docker funcional no ambiente local
- Node.js com `npm` e `npx`
- Java 21 e Maven para o backend

## Instalar browsers do Playwright
```bash
cd frontend
npx playwright install chromium
```

## Executar a suíte E2E
```bash
cd frontend
npx playwright test
```

## Escopo esperado
- cadastro e login em browser real
- criação e manipulação de tarefas em tabela e kanban
- arquivamento, desarquivamento e exclusão definitiva
- frontend e backend iniciados automaticamente durante a suíte
