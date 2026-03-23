# Feature Specification: Filtro, Busca e Ordenação na Lista de Tarefas

**Feature Branch**: `010-task-list-search-filter-sort`  
**Created**: 2026-03-20  
**Status**: Draft  
**Input**: User description: "Vamos seguir para a próxima iteração, quero implementar mecanismo de filtro, busca e ordenação na lista de tarefas."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Buscar tarefas pelo título (Priority: P1)

Como usuário autenticado, quero buscar tarefas por texto para localizar rapidamente itens na lista.

**Why this priority**: Busca textual é o ganho de usabilidade mais direto quando o volume cresce.

**Independent Test**: criar tarefas com títulos distintos, abrir a busca pela lupa no header da coluna `Tarefa`, informar um termo e validar que apenas os itens compatíveis aparecem.

**Acceptance Scenarios**:
1. **Given** tarefas ativas com títulos diferentes, **When** o usuário busca por um termo, **Then** apenas tarefas cujo título contém esse termo são exibidas.
2. **Given** tarefas arquivadas visíveis, **When** o usuário busca por um termo, **Then** a lista arquivada também respeita a busca.

---

### User Story 2 - Filtrar tarefas por status (Priority: P1)

Como usuário autenticado, quero filtrar tarefas por status para focar no estágio de trabalho que importa naquele momento.

**Why this priority**: O status já existe no domínio e precisa ser explorável na UI.

**Independent Test**: abrir o filtro da coluna `Status`, selecionar zero, um ou múltiplos status e validar o subconjunto retornado.

**Acceptance Scenarios**:
1. **Given** tarefas com múltiplos status, **When** o usuário seleciona um ou mais status no filtro da coluna, **Then** apenas tarefas desses status são exibidas.
2. **Given** o filtro ativo, **When** o usuário remove todas as seleções, **Then** a listagem completa volta a aparecer.

---

### User Story 3 - Ordenar tarefas por critérios úteis (Priority: P1)

Como usuário autenticado, quero ordenar tarefas por data ou título para ler a lista na ordem mais útil ao meu contexto.

**Why this priority**: Ordenação evita listas rígidas e melhora leitura operacional.

**Independent Test**: clicar nos headers ordenáveis da tabela e validar a ordem dos itens retornados.

**Acceptance Scenarios**:
1. **Given** tarefas com datas diferentes, **When** o usuário clica no header `Criada em`, **Then** a listagem alterna a direção e as mais novas ou antigas aparecem primeiro conforme o estado atual.
2. **Given** tarefas com títulos diferentes, **When** o usuário clica no header `Tarefa`, **Then** a listagem fica em ordem alfabética crescente ou decrescente conforme o estado atual.

---

### User Story 4 - Manter filtros coerentes entre tabela e kanban (Priority: P2)

Como usuário autenticado, quero que a visão ativa respeite busca, filtro e ordenação tanto na tabela quanto no kanban.

**Why this priority**: Evita inconsistência visual entre modos de visualização.

**Independent Test**: aplicar busca/filtro na visão tabela, alternar para kanban e validar que os mesmos critérios continuam ativos.

**Acceptance Scenarios**:
1. **Given** um termo de busca e um filtro de status ativos, **When** o usuário alterna para kanban, **Then** apenas os cartões compatíveis são exibidos.
2. **Given** o escopo `Arquivadas`, **When** o usuário visualiza tarefas arquivadas, **Then** a visualização continua em tabela com os mesmos critérios de busca e ordenação.

## Edge Cases

- Busca vazia deve se comportar como ausência de filtro textual.
- Busca deve ser case-insensitive.
- Filtro de status deve aceitar zero seleções como estado neutro (`Todos`).
- Ordenação inválida recebida pelo backend deve cair em padrão seguro.
- Kanban não deve exibir colunas vazias por erro de filtro; apenas sem cartões quando não houver itens compatíveis.

## Requirements *(mandatory)*

### Functional Requirements
- **FR-001**: O sistema MUST permitir busca textual por título de tarefa a partir da interação no header da coluna `Tarefa`.
- **FR-002**: O sistema MUST permitir filtro por status em tarefas ativas e arquivadas com seleção de zero a múltiplos status.
- **FR-003**: O sistema MUST permitir ordenação por `createdAt`, `updatedAt` e `title`.
- **FR-004**: O sistema MUST alternar a direção de ordenação `asc` e `desc` por clique no header da tabela, sem exigir controles dedicados de configuração.
- **FR-005**: O backend MUST aceitar query params para busca, filtro e ordenação na listagem de tarefas.
- **FR-006**: A visão tabela MUST aplicar busca, filtro e ordenação ao conjunto exibido.
- **FR-007**: A visão kanban MUST respeitar busca, filtro e ordenação para tarefas ativas.
- **FR-008**: O sistema MUST manter compatibilidade com a separação entre tarefas ativas e arquivadas.

### Key Entities *(include if feature involves data)*
- **TaskQuery**: combinação de `archived`, `search`, `status`, `sortBy` e `sortDirection` usada para listar tarefas.
- **TaskSortOption**: campo de ordenação suportado pela aplicação.
- **TaskFilterState**: estado da UI com busca, filtro multi-status e ordenação ativos.

## Success Criteria *(mandatory)*

### Measurable Outcomes
- **SC-001**: Usuário consegue localizar uma tarefa por termo parcial no título na lista ativa e arquivada.
- **SC-002**: Usuário consegue restringir a listagem a um status específico sem quebrar a alternância entre tabela e kanban.
- **SC-003**: Usuário consegue alterar campo e direção de ordenação clicando no header da tabela e observar mudança consistente na ordem retornada.
