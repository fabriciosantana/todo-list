# Data Model: Filtro, Busca e Ordenação na Lista de Tarefas

## TaskQuery
- `archived: boolean`
- `search: string | null`
- `statuses: TaskStatus[]`
- `sortBy: CREATED_AT | UPDATED_AT | TITLE`
- `sortDirection: ASC | DESC`

## TaskFilterState
- `searchTerm: string`
- `statuses: TaskStatus[]`
- `sortBy: 'createdAt' | 'updatedAt' | 'title'`
- `sortDirection: 'asc' | 'desc'`

## Notes
- `Task` não muda de estrutura nesta iteração.
- `TaskQuery` é transitório: representa critérios de listagem e não persistência.
