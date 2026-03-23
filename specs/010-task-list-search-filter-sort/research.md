# Research: Filtro, Busca e Ordenação na Lista de Tarefas

## Decisions

### Decision 1: Aplicar filtros no backend
- **Why**: evita carregar todas as tarefas e reduz divergência entre tabela e kanban.
- **Alternatives considered**:
  - filtrar apenas no frontend: simples, mas pouco escalável e duplicaria lógica
  - endpoints separados por combinação: desnecessariamente rígido

### Decision 2: Manter um estado único de query no frontend
- **Why**: simplifica sincronização entre tabela, kanban e escopo ativo/arquivado.
- **Alternatives considered**:
  - estados independentes por view: aumenta inconsistência

### Decision 3: Restringir ordenação a campos explícitos
- **Why**: evita expor ordenação arbitrária por query string e mantém API previsível.
- **Alternatives considered**:
  - aceitar qualquer campo textual: frágil e mais sujeito a erro
