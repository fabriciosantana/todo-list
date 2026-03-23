# Contract: Task List Query

## Request

`GET /api/tasks`

### Query Parameters
- `archived`: `true|false` (default `false`)
- `search`: termo textual opcional
- `status`: `A_FAZER|FAZENDO|CONCLUIDO` opcional, repetível para múltiplos status
- `sortBy`: `createdAt|updatedAt|title` (default `createdAt`)
- `sortDirection`: `asc|desc` (default `desc`)

## Response

Lista de `TaskResponse` já filtrada e ordenada para o usuário autenticado.
