# Data Model: Observabilidade com Grafana Cloud e LGTM local

## Telemetry Signal
- **Purpose**: representar qualquer sinal emitido pelo backend
- **Variants**:
  - `metric`
  - `trace`
  - `log`

## Cloud Observability Config
- **Purpose**: encapsular configuração externa para Grafana Cloud
- **Fields**:
  - `otlpEndpoint`
  - `otlpHeaders`
  - `lokiUrl`
  - `lokiUser`
  - `lokiPassword`
  - `environment`

## Local LGTM Config
- **Purpose**: definir o stack local de observabilidade
- **Fields**:
  - `prometheusTarget`
  - `tempoOtlpEndpoint`
  - `lokiPushUrl`
  - `grafanaUrl`
