# Feature Specification: Status de Tarefas com Visão Tabela e Kanban

**Feature Branch**: `004-task-status-kanban`  
**Created**: 2026-03-11  
**Status**: Draft  
**Input**: User description: "quero que cada tarefa tenha um status dentre os possíveis( a fazer, fazendo e concluído), além disso, na lista de tarefas o usuário deve ter a opção de visualizar a lista como tabela, como está hoje, mas incluindo o status, e também deve poder ver com um kanban."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Gerenciar status da tarefa (Priority: P1)

Como usuário autenticado, quero que cada tarefa tenha um status explícito para acompanhar se ela está a fazer, fazendo ou concluída.

**Why this priority**: O status é a mudança principal de domínio e impacta persistência, API e a leitura do progresso.

**Independent Test**: Criar, editar e consultar tarefas por API ou pela tela e verificar que cada item mantém um dos três status válidos.

**Acceptance Scenarios**:

1. **Given** um usuário autenticado, **When** ele cria uma nova tarefa sem informar status, **Then** a tarefa é salva com status `A_FAZER`.
2. **Given** uma tarefa existente, **When** o usuário altera o status para `FAZENDO` ou `CONCLUIDO`, **Then** a lista reflete o novo estado sem afetar tarefas de outros usuários.

---

### User Story 2 - Visualizar tarefas em tabela (Priority: P1)

Como usuário autenticado, quero visualizar minhas tarefas em uma tabela com colunas claras para título, status e ações.

**Why this priority**: A visão em lista atual precisa evoluir para exibir o novo status sem perder o fluxo principal de uso.

**Independent Test**: Fazer login, abrir a visão de tabela e validar presença das colunas, mudança de status e ações de editar/remover.

**Acceptance Scenarios**:

1. **Given** que o usuário está na área de tarefas, **When** seleciona a visão de tabela, **Then** o sistema exibe todas as tarefas com título, status, datas principais e ações.
2. **Given** uma tarefa na tabela, **When** o usuário altera seu status pelo seletor, **Then** a tabela atualiza a linha correspondente sem recarregar a página.

---

### User Story 3 - Visualizar tarefas em kanban (Priority: P2)

Como usuário autenticado, quero alternar para uma visão kanban para enxergar tarefas agrupadas por status.

**Why this priority**: O kanban amplia a leitura operacional do backlog, mas depende do status já existir e da listagem estar funcional.

**Independent Test**: Alternar da visão de tabela para kanban e validar que as tarefas aparecem nas colunas `A Fazer`, `Fazendo` e `Concluído`, com atualização de status preservando consistência.

**Acceptance Scenarios**:

1. **Given** tarefas com status diferentes, **When** o usuário seleciona a visão kanban, **Then** cada tarefa aparece na coluna correspondente ao seu status.
2. **Given** uma tarefa em uma coluna do kanban, **When** o usuário muda o status pelo card, **Then** a tarefa some da coluna atual e aparece na nova coluna.
3. **Given** uma tarefa em um quadro do kanban, **When** o usuário arrasta o card para outro quadro, **Then** o status da tarefa é atualizado para o quadro de destino.

### User Story 4 - Arquivar e recuperar tarefas (Priority: P2)

Como usuário autenticado, quero arquivar tarefas para escondê-las da lista principal e consultá-las separadamente quando precisar restaurá-las.

**Why this priority**: Arquivamento reduz ruído operacional sem apagar histórico, mas depende da estrutura de listagem já existente.

**Independent Test**: Arquivar uma tarefa ativa, confirmar que ela some das visões principais, abrir a lista de arquivadas e desarquivar o item.

**Acceptance Scenarios**:

1. **Given** uma tarefa ativa, **When** o usuário a arquiva, **Then** ela deixa de aparecer nas visões de tarefas ativas.
2. **Given** uma tarefa arquivada, **When** o usuário acessa a lista de arquivadas e a desarquiva, **Then** ela volta para a lista ativa.
3. **Given** uma tarefa arquivada, **When** o usuário escolhe excluí-la, **Then** o sistema pede confirmação explícita e informa que a ação é irreversível.

### Edge Cases

- Tarefas legadas gravadas com o campo `completed` devem ser migradas para um status equivalente sem perda de dados.
- Requisições com status inválido devem ser rejeitadas pela API com erro de validação.
- A troca entre tabela e kanban não deve perder mensagens, autenticação ou estado da lista carregada.
- Listas vazias devem ter estado vazio consistente nas duas visões.
- Tarefas arquivadas não devem aparecer nas consultas padrão de tarefas ativas.
- Tarefas ativas não devem poder ser excluídas definitivamente sem arquivamento prévio.
- Mensagens de sucesso e erro devem sumir automaticamente sem bloquear novas ações do usuário.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: O sistema MUST persistir um status por tarefa com os valores `A_FAZER`, `FAZENDO` e `CONCLUIDO`.
- **FR-002**: O sistema MUST migrar dados existentes baseados em `completed` para o novo campo `status`.
- **FR-003**: Usuários MUST poder criar tarefas com status inicial, usando `A_FAZER` como padrão quando nada for informado.
- **FR-004**: Usuários MUST poder atualizar o status de uma tarefa existente.
- **FR-005**: O sistema MUST expor o status nas respostas da API de tarefas.
- **FR-006**: O frontend MUST permitir alternar entre visão em tabela e visão kanban sem novo login.
- **FR-007**: A visão em tabela MUST exibir o status de cada tarefa e permitir sua alteração.
- **FR-008**: A visão kanban MUST agrupar tarefas por status e refletir mudanças imediatamente após atualização.
- **FR-009**: O sistema MUST permitir arquivar tarefas sem removê-las definitivamente.
- **FR-010**: O sistema MUST permitir listar apenas tarefas arquivadas.
- **FR-011**: O sistema MUST permitir desarquivar tarefas.
- **FR-012**: A visão kanban MUST permitir mover tarefas entre colunas por drag and drop.
- **FR-013**: O sistema MUST permitir exclusão definitiva apenas para tarefas arquivadas.
- **FR-014**: O sistema MUST solicitar confirmação antes da exclusão definitiva e informar que a ação é irreversível.
- **FR-015**: O frontend MUST exibir mensagens de sucesso e erro com fechamento manual e desaparecimento automático após 5 segundos.
- **FR-016**: Todas as operações MUST continuar isoladas por usuário autenticado.

### Key Entities *(include if feature involves data)*

- **Task**: unidade de trabalho do usuário com título, status, flag de arquivamento, datas de criação/atualização e owner.
- **TaskStatus**: enumeração de domínio que representa `A_FAZER`, `FAZENDO` e `CONCLUIDO`.
- **TaskViewMode**: preferência de apresentação do usuário na tela atual, com valores `table` e `kanban`.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% das tarefas retornadas pela API possuem um status válido após a migração.
- **SC-002**: Um usuário autenticado consegue criar uma tarefa e mudar seu status em até 3 interações na visão de tabela.
- **SC-003**: A alternância entre tabela e kanban ocorre sem recarregar a página e em menos de 1 segundo em ambiente local.
- **SC-004**: Uma tarefa arquivada deixa de aparecer na lista ativa em uma atualização imediata da interface.
- **SC-005**: O usuário consegue mover uma tarefa entre colunas no kanban com uma única operação de arrastar e soltar.
- **SC-006**: Cadastro, login, listagem e atualização de tarefas continuam compilando e passando nas validações automatizadas do projeto.
