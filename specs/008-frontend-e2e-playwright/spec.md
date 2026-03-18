# Feature Specification: Testes E2E do Frontend com Playwright

**Feature Branch**: `008-frontend-e2e-playwright`  
**Created**: 2026-03-17  
**Status**: Draft  
**Input**: User description: "vamos seguir sua sugestão."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Validar autenticação no browser real (Priority: P1)

Como mantenedor do produto, quero validar cadastro e login em browser real para detectar regressões de UI, roteamento e autenticação que não aparecem em testes unitários.

**Why this priority**: Cadastro e login são a porta de entrada do sistema e quebram o uso inteiro quando falham.

**Independent Test**: Executar um teste Playwright que cadastra um usuário novo, encerra a sessão e faz login novamente com sucesso.

**Acceptance Scenarios**:
1. **Given** a aplicação carregada em browser real, **When** um usuário novo se cadastra, **Then** a sessão é iniciada e a área autenticada fica visível.
2. **Given** uma sessão encerrada, **When** o mesmo usuário faz login novamente, **Then** a área autenticada volta a ser exibida sem erro.

---

### User Story 2 - Validar o fluxo principal de tarefas ponta a ponta (Priority: P1)

Como mantenedor do produto, quero validar criação e manipulação de tarefas pelo frontend contra o backend real para garantir que os fluxos essenciais do usuário continuam funcionando.

**Why this priority**: CRUD de tarefas, mudança de status e arquivamento concentram o valor central do produto.

**Independent Test**: Executar um teste Playwright que cria tarefas, alterna entre tabela e kanban, move status, arquiva, desarquiva e exclui definitivamente.

**Acceptance Scenarios**:
1. **Given** um usuário autenticado, **When** cria uma tarefa, altera status na tabela e no kanban, **Then** a UI reflete os novos estados corretamente.
2. **Given** uma tarefa arquivada, **When** o usuário acessa a visão de arquivadas, desarquiva e depois exclui outra tarefa arquivada, **Then** a tarefa volta para a lista ativa ou desaparece definitivamente conforme a ação.

---

### User Story 3 - Permitir execução local no Codespaces (Priority: P2)

Como mantenedor do projeto, quero rodar a suíte E2E no Codespaces sem configuração manual frágil de browser para reproduzir a validação localmente e no CI.

**Why this priority**: O ambiente de Codespaces é um alvo explícito desta iteração.

**Independent Test**: Seguir o quickstart, instalar browsers do Playwright e executar os testes E2E com frontend e backend iniciados automaticamente.

**Acceptance Scenarios**:
1. **Given** um Codespace com Docker e Node disponíveis, **When** o mantenedor executa o comando documentado, **Then** a suíte Playwright sobe os serviços necessários e roda headless.

## Edge Cases

- O banco local pode conter dados antigos; os testes precisam usar usuários únicos por execução.
- O frontend deve aguardar a API real antes de interagir com a UI autenticada.
- Confirmações nativas de exclusão precisam ser tratadas explicitamente no browser.
- A movimentação no kanban deve ser testada com ação de drag and drop ou fallback estável no browser real.

## Requirements *(mandatory)*

### Functional Requirements
- **FR-001**: O repositório MUST incluir configuração do Playwright para execução local e em CI.
- **FR-002**: O projeto MUST possuir pelo menos um teste E2E cobrindo cadastro e login completos.
- **FR-003**: O projeto MUST possuir pelo menos um teste E2E cobrindo criação, atualização de status, arquivamento, desarquivamento e exclusão de tarefas arquivadas.
- **FR-004**: A execução E2E MUST ser compatível com Codespaces usando os browsers gerenciados pelo Playwright.
- **FR-005**: A documentação MUST explicar como instalar os browsers, subir a infraestrutura e executar a suíte E2E.

### Key Entities *(include if feature involves data)*
- **E2E User Session**: sessão criada no browser por cadastro ou login reais.
- **E2E Task Flow**: sequência de criação, movimentação, arquivamento, desarquivamento e exclusão de tarefas via UI.
- **Codespaces Runtime**: ambiente local com Docker, backend Spring Boot, frontend Angular e browser do Playwright.

## Success Criteria *(mandatory)*

### Measurable Outcomes
- **SC-001**: A suíte Playwright executa em modo headless no Codespaces sem depender de Chrome do sistema.
- **SC-002**: Os cenários E2E de autenticação e tarefas passam localmente com backend e frontend reais.
- **SC-003**: O quickstart documenta os comandos necessários para instalação de browsers e execução da suíte.
