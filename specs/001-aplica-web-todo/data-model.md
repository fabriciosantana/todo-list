# Data Model

## Task

- `id` (string): identificador único.
- `title` (string): texto da tarefa (1..120 chars).
- `completed` (boolean): status da tarefa.
- `createdAt` (string ISO date): data de criação.
- `updatedAt` (string ISO date): data da última atualização.

## FilterState

- `value` (enum): `all | active | completed`

## Storage Schema

Chave: `todo-list.tasks.v1`

```json
[
  {
    "id": "1740000000000",
    "title": "Comprar leite",
    "completed": false,
    "createdAt": "2026-03-05T14:00:00.000Z",
    "updatedAt": "2026-03-05T14:00:00.000Z"
  }
]
```
