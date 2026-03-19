# Feature Specification: Observabilidade com Grafana Cloud e LGTM local

**Feature Branch**: `009-observability-grafana-cloud`  
**Created**: 2026-03-18  
**Status**: Draft  
**Input**: User description: "Na próxima iteração implementar observabilidade com a seguinte estrutura"

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Observar métricas do backend localmente (Priority: P1)

Como mantenedor do backend, quero expor métricas operacionais e de aplicação para inspecionar saúde, latência e comportamento do serviço localmente.

**Why this priority**: Métricas são a base para dashboards e alertas.

**Independent Test**: subir o backend e confirmar que o endpoint Prometheus está disponível e que o Prometheus local consegue coletá-lo.

**Acceptance Scenarios**:
1. **Given** o backend rodando localmente, **When** `GET /actuator/prometheus` é acessado, **Then** métricas Prometheus são retornadas.
2. **Given** a stack LGTM local ativa, **When** o Prometheus coleta o backend, **Then** as métricas aparecem no Grafana local.

---

### User Story 2 - Exportar traces e métricas para Grafana Cloud (Priority: P1)

Como mantenedor do sistema, quero que o backend envie telemetria para o Grafana Cloud para ter observabilidade fora do ambiente local.

**Why this priority**: A estratégia da iteração exige cloud como destino principal.

**Independent Test**: configurar variáveis de ambiente OTLP/Grafana Cloud e validar que o backend inicia com exportadores ativos.

**Acceptance Scenarios**:
1. **Given** credenciais e endpoints OTLP válidos, **When** o backend inicia, **Then** métricas e traces são exportados sem erro fatal.
2. **Given** as credenciais não estão definidas, **When** o backend inicia localmente, **Then** a aplicação continua funcional usando apenas a stack local.

---

### User Story 3 - Enviar logs estruturados para Loki/Grafana Cloud (Priority: P1)

Como mantenedor do sistema, quero logs estruturados e exportáveis para Loki para facilitar correlação com traces e métricas.

**Why this priority**: Logs são um dos três pilares da observabilidade.

**Independent Test**: gerar logs do backend localmente e confirmar o formato estruturado e a configuração de envio para Loki.

**Acceptance Scenarios**:
1. **Given** o backend rodando localmente, **When** ele escreve logs, **Then** os logs saem em formato estruturado legível por ferramenta.
2. **Given** URL e credenciais de Loki válidas, **When** o backend escreve logs, **Then** eles podem ser encaminhados para o destino configurado.

---

### User Story 4 - Subir LGTM self-hosted apenas localmente (Priority: P2)

Como mantenedor do projeto, quero uma stack LGTM local via Docker Compose para desenvolvimento e troubleshooting sem depender da nuvem.

**Why this priority**: A estratégia híbrida da iteração separa local e cloud.

**Independent Test**: subir o `docker compose` local de observabilidade e abrir o Grafana com datasources provisionados.

**Acceptance Scenarios**:
1. **Given** Docker disponível, **When** o mantenedor sobe a stack local, **Then** Grafana, Loki, Tempo e Prometheus ficam acessíveis.

## Edge Cases

- O backend deve continuar funcionando mesmo sem credenciais de Grafana Cloud.
- O ambiente local deve aceitar execução do backend fora de contêiner, com Prometheus no Docker raspando o host.
- Logs não devem depender exclusivamente da nuvem para existir.
- A configuração não deve expor segredos diretamente em arquivos versionados.

## Requirements *(mandatory)*

### Functional Requirements
- **FR-001**: O backend MUST expor Actuator com endpoint Prometheus habilitado.
- **FR-002**: O backend MUST gerar logs estruturados.
- **FR-003**: O backend MUST suportar exportação de traces e métricas para Grafana Cloud via variáveis de ambiente.
- **FR-004**: O backend MUST suportar envio de logs para Loki via configuração externa.
- **FR-005**: O projeto MUST incluir uma stack LGTM local via Docker Compose.
- **FR-006**: A documentação MUST explicar o modo local self-hosted e o modo cloud com Grafana Cloud Free.

### Key Entities *(include if feature involves data)*
- **Telemetry Signal**: métrica, trace ou log gerado pelo backend.
- **Cloud Observability Config**: conjunto de endpoints, headers e variáveis para Grafana Cloud.
- **Local LGTM Stack**: conjunto Grafana, Loki, Tempo e Prometheus usado apenas localmente.

## Success Criteria *(mandatory)*

### Measurable Outcomes
- **SC-001**: `GET /actuator/prometheus` responde com métricas no backend local.
- **SC-002**: a stack LGTM local sobe via Docker Compose com datasources provisionados.
- **SC-003**: o backend inicia com ou sem credenciais de Grafana Cloud, sem quebrar o fluxo funcional da aplicação.
