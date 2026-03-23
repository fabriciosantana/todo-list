# Quickstart: Filtro, Busca e Ordenação na Lista de Tarefas

## Backend

```bash
cd backend
mvn test
```

## Frontend

```bash
cd frontend
npm ci
npm run test:ci
```

## Validação manual

1. Fazer login.
2. Criar tarefas com títulos e status distintos.
3. Clicar na lupa do header da coluna `Tarefa` e buscar por termo parcial no título.
4. Abrir o filtro da coluna `Status` e selecionar zero, um ou múltiplos status.
5. Clicar nos headers ordenáveis da tabela para alternar ordenação entre `Criação`, `Atualização` e `Título`.
6. Alternar entre tabela e kanban nas tarefas ativas e confirmar coerência.
7. Abrir `Arquivadas` e validar busca/ordenação na tabela.
