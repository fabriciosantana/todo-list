# UI Behavior Contract

## Add Task

- Input não pode estar vazio após `trim()`.
- Ao sucesso, nova tarefa é adicionada com `completed=false`.

## Toggle Task

- Clique no checkbox alterna `completed`.
- Alteração deve persistir no `localStorage`.

## Edit Task

- Ação `Editar` solicita novo título.
- Título vazio após `trim()` invalida a atualização.

## Remove Task

- Ação `Remover` exclui tarefa da lista e do armazenamento.

## Filter

- `Todas`: mostra tudo.
- `Pendentes`: mostra `completed=false`.
- `Concluídas`: mostra `completed=true`.
