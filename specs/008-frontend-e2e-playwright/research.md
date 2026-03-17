# Research: Testes E2E do Frontend com Playwright

## Decisão: usar `@playwright/test` no frontend
- **Rationale**: fornece runner, fixtures, traces, screenshots e integração simples com CI.
- **Alternatives considered**:
  - `playwright-cli`: útil para depuração manual, mas inadequado como suíte versionada
  - Cypress: adicionaria outra stack sem vantagem clara para este projeto

## Decisão: usar browsers gerenciados pelo Playwright
- **Rationale**: evita dependência de Chrome do sistema e reduz atrito no Codespaces.
- **Alternatives considered**:
  - usar Chrome/Chromium do sistema: já se mostrou frágil no ambiente local

## Decisão: subir backend e frontend pelo `webServer` do Playwright
- **Rationale**: reduz setup manual e torna a execução repetível localmente e no CI.
- **Alternatives considered**:
  - exigir processos externos iniciados manualmente: mais frágil e mais propenso a erro humano

## Decisão: usar dados únicos por execução
- **Rationale**: evita dependência de limpeza completa do banco entre cenários e reduz flakiness.
- **Alternatives considered**:
  - resetar banco inteiro a cada teste: mais lento e mais invasivo
