# Research: Observabilidade com Grafana Cloud e LGTM local

## Decisão: Grafana Cloud como destino principal
- **Rationale**: reduz custo operacional e atende a necessidade de métricas, logs e traces no ambiente cloud.
- **Alternatives considered**:
  - self-hosted em nuvem: maior esforço operacional e free tiers instáveis

## Decisão: LGTM local via Docker Compose
- **Rationale**: bom para desenvolvimento, troubleshooting e aprendizado sem acoplar a nuvem ao ciclo local.
- **Alternatives considered**:
  - usar apenas Grafana Cloud: perde autonomia e dificulta experimentação local

## Decisão: backend-first
- **Rationale**: o backend concentra endpoints, autenticação, banco e fluxos principais; é o ponto de maior retorno inicial.
- **Alternatives considered**:
  - instrumentar frontend nesta mesma iteração: escopo maior do que o necessário

## Decisão: logs estruturados + métricas Prometheus + exportadores OTLP
- **Rationale**: equilíbrio entre simplicidade e compatibilidade com Grafana Cloud/LGTM.
- **Alternatives considered**:
  - observabilidade apenas por logs: insuficiente
  - observabilidade apenas por métricas: insuficiente
