# Feature Specification: Testes de Integração do Backend com Testcontainers

**Feature Branch**: `007-backend-integration-tests`  
**Created**: 2026-03-17  
**Status**: Draft  
**Input**: User description: "vamos seguir para a iteração 007"

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Validar persistência real com PostgreSQL (Priority: P1)

Como mantenedor do backend, quero validar repositórios e migrations contra um PostgreSQL real para reduzir falsos positivos de testes com mocks.

**Why this priority**: Persistência e schema são pontos onde os unit tests não observam falhas reais.

**Independent Test**: Executar testes de integração JPA com Testcontainers e confirmar queries e Flyway funcionando em banco real.

**Acceptance Scenarios**:
1. **Given** um banco limpo iniciado via Testcontainers, **When** o contexto JPA sobe, **Then** as migrations Flyway são aplicadas sem erro.
2. **Given** usuários e tarefas persistidos, **When** os repositórios são consultados, **Then** os resultados respeitam owner, status e arquivamento.

---

### User Story 2 - Validar fluxos HTTP principais com banco real (Priority: P1)

Como mantenedor do backend, quero executar fluxos HTTP de autenticação e tarefas contra a aplicação Spring com PostgreSQL real para testar wiring, segurança e serialização.

**Why this priority**: Cadastro, login e CRUD de tarefas são os fluxos críticos do produto.

**Independent Test**: Executar testes `@SpringBootTest` com `MockMvc` e Testcontainers cobrindo autenticação e manipulação de tarefas.

**Acceptance Scenarios**:
1. **Given** um usuário novo, **When** `POST /api/auth/register` é chamado, **Then** o usuário é persistido e o JWT é retornado.
2. **Given** um usuário autenticado, **When** cria/lista/arquiva/desarquiva tarefas, **Then** a API responde corretamente e o banco reflete o estado esperado.

---

### User Story 3 - Documentar execução local dos testes de integração (Priority: P2)

Como mantenedor do projeto, quero documentação clara de como rodar os testes de integração para repetir a validação em ambiente local e CI.

**Why this priority**: Testes com container exigem pré-requisitos explícitos.

**Independent Test**: Seguir o quickstart e executar a suíte de integração localmente com Docker disponível.

**Acceptance Scenarios**:
1. **Given** Docker local disponível, **When** o mantenedor segue o quickstart, **Then** os testes de integração executam sem configuração oculta.

## Edge Cases

- O banco precisa iniciar limpo a cada suíte para não mascarar falhas de schema.
- Requisições autenticadas devem falhar sem token válido.
- Tarefas de um usuário não podem aparecer para outro usuário nos testes de integração.

## Requirements *(mandatory)*

### Functional Requirements
- **FR-001**: O backend MUST incluir dependências de Testcontainers para PostgreSQL e JUnit.
- **FR-002**: O projeto MUST possuir pelo menos um teste de integração JPA com PostgreSQL real.
- **FR-003**: O projeto MUST possuir testes HTTP de autenticação e tarefas com `MockMvc` e banco real.
- **FR-004**: Os testes de integração MUST usar as migrations Flyway reais do projeto.
- **FR-005**: A documentação MUST explicar pré-requisitos e comando para execução local.

### Key Entities *(include if feature involves data)*
- **Integration Database**: instância PostgreSQL efêmera criada por Testcontainers.
- **Authenticated Flow**: sequência HTTP com obtenção e uso de JWT em endpoints protegidos.
- **Repository Contract**: comportamento observável de consultas JPA em banco real.

## Success Criteria *(mandatory)*

### Measurable Outcomes
- **SC-001**: `mvn test` executa unit tests e testes de integração com PostgreSQL real sem intervenção manual além de Docker ativo.
- **SC-002**: Os fluxos de registro/login/tarefas passam em teste de integração end-to-end do backend.
- **SC-003**: O quickstart documenta o comando e o pré-requisito de Docker.
