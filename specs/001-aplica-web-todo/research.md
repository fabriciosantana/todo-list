# Research Notes

## Decision 1: Persistência em localStorage

- **Decision**: Armazenar tarefas no `localStorage` com chave única do app.
- **Rationale**: Atende MVP local-first sem backend.
- **Alternatives considered**:
  - `sessionStorage`: perde dados ao fechar aba.
  - IndexedDB: maior complexidade para escopo inicial.

## Decision 2: JavaScript vanilla

- **Decision**: Implementar lógica com JavaScript puro.
- **Rationale**: Menos dependências e setup imediato.
- **Alternatives considered**:
  - React/Vue: adiciona build tooling desnecessário para MVP.

## Decision 3: Edição inline com prompt

- **Decision**: Permitir edição por `prompt()` no primeiro incremento.
- **Rationale**: Simplicidade e baixo custo de implementação.
- **Alternatives considered**:
  - Modal customizado: UX melhor, porém maior esforço inicial.
