# Feature Specification: Melhorar Cobertura de Testes

**Feature Branch**: `006-improve-test-coverage`  
**Created**: 2026-03-17  
**Status**: Draft  
**Input**: User description: "vamos aprimorar a cobertura"

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Cobrir serviços críticos do frontend (Priority: P1)

Como mantenedor do projeto, quero testes unitários dos serviços de autenticação e tarefas no Angular para detectar regressões de integração com a API e persistência de sessão.

**Why this priority**: Esses serviços concentram efeitos colaterais e hoje têm cobertura muito baixa.

**Independent Test**: Executar `npm run test:ci` e confirmar cobertura para `auth.service.ts` e `task.service.ts`.

**Acceptance Scenarios**:

1. **Given** um cadastro ou login bem-sucedido, **When** o serviço recebe a resposta, **Then** token e usuário são persistidos no `localStorage`.
2. **Given** chamadas de listagem, criação e arquivamento de tarefas, **When** os métodos são invocados, **Then** as requisições HTTP usam rotas e parâmetros esperados.

---

### User Story 2 - Cobrir regras críticas do backend (Priority: P1)

Como mantenedor do projeto, quero testes unitários para autenticação e JWT no backend para validar regras de domínio e segurança sem depender de integração completa.

**Why this priority**: Regras de autenticação e geração de token são centrais e qualquer regressão afeta login da aplicação inteira.

**Independent Test**: Executar `mvn test jacoco:report` e confirmar cobertura adicional em `AuthService`, `JwtService` e `AppUserDetailsService`.

**Acceptance Scenarios**:

1. **Given** um e-mail já existente, **When** o cadastro é tentado, **Then** o serviço retorna conflito.
2. **Given** um login válido, **When** o usuário é autenticado, **Then** o token JWT e os dados do usuário são retornados.
3. **Given** um segredo JWT em Base64 ou texto simples, **When** o token é gerado e validado, **Then** a extração do usuário funciona corretamente.

---

### User Story 3 - Medir cobertura com foco em regressão real (Priority: P2)

Como mantenedor do projeto, quero que a documentação e os artefatos da iteração deixem claro como validar a cobertura localmente para repetir o processo sem adivinhação.

**Why this priority**: Sem ritual claro, a melhoria de cobertura se perde na próxima iteração.

**Independent Test**: Seguir `quickstart.md` e reproduzir backend + frontend com cobertura local.

**Acceptance Scenarios**:

1. **Given** um ambiente local pronto, **When** os comandos documentados são executados, **Then** os relatórios de cobertura são gerados sem passos implícitos.

## Edge Cases

- `localStorage` com JSON inválido não deve quebrar `AuthService.getUser()`.
- JWT com segredo não Base64 deve continuar funcional pelo fallback SHA-256.
- Métodos do componente principal que dependem de `window.prompt` e `window.confirm` devem ser testados via mocks, sem interação real.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: O frontend MUST possuir testes unitários cobrindo `AuthService` e `TaskService`.
- **FR-002**: O frontend MUST ampliar cobertura do componente principal em fluxos de autenticação e tarefas.
- **FR-003**: O backend MUST possuir testes unitários cobrindo `AuthService`, `JwtService` e `AppUserDetailsService`.
- **FR-004**: Os testes MUST validar casos de sucesso e falha nas regras críticas.
- **FR-005**: A documentação MUST registrar os comandos locais relevantes para repetir a validação de cobertura.

### Key Entities *(include if feature involves data)*

- **Coverage Target**: arquivo/classe crítica cuja cobertura deve crescer nesta iteração.
- **Auth Session**: combinação de token JWT e usuário persistido no frontend.
- **Task Command**: operação de tarefa que gera requisição HTTP ou regra de domínio observável.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: `npm run test:ci` executa com sucesso e inclui testes dos serviços Angular críticos.
- **SC-002**: `mvn test jacoco:report` executa com sucesso e cobre autenticação/JWT no backend.
- **SC-003**: A cobertura do frontend sobe além do nível atual de smoke test simples.
- **SC-004**: A iteração documenta explicitamente como reproduzir os relatórios localmente.
