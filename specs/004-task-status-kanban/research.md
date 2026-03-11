# Research: Status de Tarefas com Visão Tabela e Kanban

## Decisão 1: substituir `completed` por enum de domínio
- **Decisão**: usar `TaskStatus` como enum persistido em `VARCHAR`.
- **Racional**: `boolean` não comporta os três estados pedidos e dificulta expansão futura.
- **Alternativas consideradas**:
  - Manter `completed` e inferir `fazendo` no frontend: rejeitado por quebrar consistência de domínio.
  - Criar campos `completed` + `inProgress`: rejeitado por duplicar estado e abrir combinações inválidas.

## Decisão 2: migrar dados legados com Flyway
- **Decisão**: criar `V2__add_task_status.sql` para adicionar `status`, popular com base em `completed` e remover a coluna antiga.
- **Racional**: mantém rastreabilidade da mudança e permite deploy automático consistente no Supabase/Render.
- **Alternativas consideradas**:
  - Alterar `V1__init.sql`: rejeitado porque o banco de produção já possui histórico Flyway.
  - Usar `ddl-auto`: rejeitado porque produção já está padronizada com Flyway.

## Decisão 3: expor duas visões no mesmo componente Angular
- **Decisão**: manter a tela de tarefas no componente principal e alternar entre modos `table` e `kanban` por estado local.
- **Racional**: reduz escopo, evita roteamento novo e preserva fluxo atual de autenticação.
- **Alternativas consideradas**:
  - Criar páginas separadas: rejeitado por aumentar acoplamento e custo de navegação sem ganho real.

## Decisão 4: edição de status por seletor explícito
- **Decisão**: usar `select` de status na tabela e nos cards do kanban.
- **Racional**: é o caminho mais previsível para os três estados e funciona bem em desktop e mobile.
- **Alternativas consideradas**:
  - Drag and drop no kanban: rejeitado nesta iteração por aumentar complexidade e superfície de bugs.
