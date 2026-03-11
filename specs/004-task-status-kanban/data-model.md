# Data Model: Status de Tarefas com Visão Tabela e Kanban

## TaskStatus
- **Descrição**: enum de domínio persistido no banco e exposto pela API.
- **Valores**:
  - `A_FAZER`
  - `FAZENDO`
  - `CONCLUIDO`

## Task
- **Descrição**: tarefa pertencente a um usuário autenticado.
- **Campos**:
  - `id: Long`
  - `title: String (1..120)`
  - `status: TaskStatus`
  - `archived: boolean`
  - `createdAt: Instant`
  - `updatedAt: Instant`
  - `owner: User`
- **Regras**:
  - toda tarefa deve possuir `status` válido.
  - ao criar sem status informado, usar `A_FAZER`.
  - tarefas arquivadas não aparecem na consulta padrão de tarefas ativas.
  - `owner` continua obrigatório.

## TaskRequest
- **Descrição**: payload de entrada para criação/edição.
- **Campos**:
  - `title: String`
  - `status: TaskStatus | null`

## TaskResponse
- **Descrição**: payload de saída da API.
- **Campos**:
  - `id`
  - `title`
  - `status`
  - `archived`
  - `createdAt`
  - `updatedAt`

## TaskViewMode
- **Descrição**: estado de UI do frontend.
- **Valores**:
  - `table`
  - `kanban`

## TaskScope
- **Descrição**: filtro principal da UI entre itens ativos e arquivados.
- **Valores**:
  - `active`
  - `archived`
