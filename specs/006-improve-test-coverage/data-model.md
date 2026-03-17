# Data Model: Melhorar Cobertura de Testes

## Entities

### Coverage Target
- **Fields**:
  - `name`: identificador do arquivo/classe
  - `layer`: `frontend` ou `backend`
  - `risk`: motivo do alvo ser crítico

### Auth Session
- **Fields**:
  - `token`
  - `user.id`
  - `user.email`

### Task Command
- **Fields**:
  - `method`
  - `url`
  - `payload`
  - `expectedEffect`
