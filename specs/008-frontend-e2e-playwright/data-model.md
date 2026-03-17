# Data Model: Testes E2E do Frontend com Playwright

## E2E User Session
- **Purpose**: representar um usuário de teste autenticado no browser real
- **Fields**:
  - `name`
  - `email`
  - `password`
  - `token` (implícito via aplicação)

## E2E Task Fixture
- **Purpose**: representar tarefas criadas durante os cenários E2E
- **Fields**:
  - `title`
  - `status`
  - `archived`

## Runtime URLs
- **Purpose**: centralizar os endpoints usados pelos testes
- **Fields**:
  - `frontendBaseUrl`
  - `backendBaseUrl`
