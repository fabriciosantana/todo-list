# Feature Specification: Qualidade e Cobertura com SonarCloud

**Feature Branch**: `005-sonarcloud-quality`  
**Created**: 2026-03-16  
**Status**: Draft  
**Input**: User description: "Nessa iteração quero configurar o sonarqube (https://sonarcloud.io) para avaliar a qualidade do código, cobertura de código e demais aspectos de qualidade."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Analisar backend e frontend no SonarCloud (Priority: P1)

Como mantenedor do projeto, quero que o repositório envie análises automáticas para o SonarCloud para acompanhar bugs, code smells, vulnerabilidades e hotspots.

**Why this priority**: Sem a análise centralizada, a qualidade continua invisível no pipeline principal.

**Independent Test**: Executar o workflow de qualidade com credenciais válidas e confirmar que o projeto aparece atualizado no SonarCloud.

**Acceptance Scenarios**:

1. **Given** credenciais e parâmetros do SonarCloud configurados, **When** o workflow roda, **Then** backend e frontend são analisados no mesmo projeto SonarCloud.
2. **Given** um pull request, **When** a análise executa, **Then** o SonarCloud recebe dados suficientes para quality gate e comentários de qualidade.

---

### User Story 2 - Publicar cobertura de testes (Priority: P1)

Como mantenedor do projeto, quero gerar relatórios de cobertura para backend e frontend para que o SonarCloud avalie cobertura real e código não testado.

**Why this priority**: Sonar sem cobertura perde uma parte central do objetivo desta iteração.

**Independent Test**: Rodar os comandos locais de teste e validar a geração dos arquivos `jacoco.xml` e `lcov.info` usados pelo scanner.

**Acceptance Scenarios**:

1. **Given** o backend configurado com JaCoCo, **When** os testes Maven rodam, **Then** o relatório XML de cobertura é gerado.
2. **Given** o frontend configurado para teste headless com coverage, **When** os testes Angular rodam, **Then** o arquivo `lcov.info` é gerado.

---

### User Story 3 - Documentar setup operacional (Priority: P2)

Como mantenedor do projeto, quero documentação clara de secrets, variáveis e comandos para operar o SonarCloud sem adivinhação.

**Why this priority**: A integração só se sustenta se outro membro do time conseguir reproduzir e manter a configuração.

**Independent Test**: Seguir o README e o quickstart para configurar as variáveis e executar a análise local/pipeline.

**Acceptance Scenarios**:

1. **Given** um ambiente novo, **When** o mantenedor segue a documentação, **Then** ele consegue configurar `SONAR_TOKEN`, organização e chave do projeto.
2. **Given** a documentação atualizada, **When** o pipeline falha, **Then** o responsável sabe onde verificar cobertura, scanner e variáveis do SonarCloud.

### Edge Cases

- Execuções sem `SONAR_TOKEN` devem falhar com causa clara.
- Se o navegador headless não estiver disponível, o pipeline frontend deve instalar/configurar um browser compatível.
- Mudanças em arquivos gerados não devem distorcer os indicadores de cobertura e qualidade.
- O scanner não deve incluir diretórios de build (`target`, `dist`, `coverage`) como código fonte analisado.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: O projeto MUST possuir configuração de scanner para SonarCloud versionada no repositório.
- **FR-002**: O backend MUST gerar cobertura XML com JaCoCo durante a execução de testes.
- **FR-003**: O frontend MUST gerar cobertura LCOV em execução headless adequada para CI.
- **FR-004**: O GitHub Actions MUST executar testes e análise SonarCloud em fluxo dedicado de qualidade.
- **FR-005**: O workflow MUST consumir credenciais e identificadores do SonarCloud por secrets/variables do GitHub.
- **FR-006**: O scanner MUST excluir artefatos gerados e dependências da análise de código fonte.
- **FR-007**: A documentação MUST explicar como configurar SonarCloud, coverage e quality gate.

### Key Entities *(include if feature involves data)*

- **Sonar Project**: projeto remoto no SonarCloud que recebe métricas, issues e cobertura.
- **Coverage Report**: artefato de teste consumido pelo scanner (`jacoco.xml` e `lcov.info`).
- **Quality Workflow**: pipeline GitHub Actions responsável por testes, cobertura e análise.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: O workflow de qualidade executa testes backend e frontend e conclui análise SonarCloud em uma única execução.
- **SC-002**: Os relatórios `backend/target/site/jacoco/jacoco.xml` e `frontend/coverage/frontend/lcov.info` são gerados com sucesso.
- **SC-003**: O README documenta 100% das variáveis necessárias para ativar a integração.
- **SC-004**: A configuração local e de CI mantém builds existentes de backend e frontend operacionais.
