# Contract: Task Status and View Modes

## REST payloads

### GET /api/tasks
Cada item deve retornar:
- `id: number`
- `title: string`
- `status: "A_FAZER" | "FAZENDO" | "CONCLUIDO"`
- `archived: boolean`
- `createdAt: string`
- `updatedAt: string`

### GET /api/tasks?archived=true
Retorna apenas tarefas arquivadas do usuário autenticado.

### POST /api/tasks
Payload aceito:
```json
{
  "title": "Preparar release",
  "status": "A_FAZER"
}
```

`status` é opcional. Quando ausente, o backend aplica `A_FAZER`.

### PUT /api/tasks/{id}
Payload aceito:
```json
{
  "title": "Preparar release",
  "status": "FAZENDO"
}
```

### PATCH /api/tasks/{id}/archive
Arquiva a tarefa e a remove da lista ativa.

### PATCH /api/tasks/{id}/unarchive
Desarquiva a tarefa e a devolve para a lista ativa.

### DELETE /api/tasks/{id}
Remove permanentemente a tarefa. A UI só deve oferecer essa ação para tarefas arquivadas.

## UI states
- O usuário pode alternar entre `table` e `kanban` sem nova chamada de autenticação.
- O usuário pode alternar entre `active` e `archived` para visualizar tarefas ativas ou arquivadas.
- A visão `table` deve exibir colunas de título, status, criação e ações.
- A visão `kanban` deve exibir três colunas, uma para cada `TaskStatus`.
- A visão `kanban` deve permitir mover cards por drag and drop entre colunas.
- Mudanças de status devem refletir na coleção local sem recarregar a página inteira.
- Mensagens de feedback devem expirar automaticamente em 5 segundos, com contador discreto e opção de fechamento manual.
