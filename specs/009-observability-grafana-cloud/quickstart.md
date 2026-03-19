# Quickstart: Observabilidade com Grafana Cloud e LGTM local

## Pré-requisitos
- Docker funcional no ambiente local
- Java 21 e Maven

## Local

1. Subir a stack LGTM:

```bash
docker compose -f observability/docker-compose.yml up -d
```

2. Iniciar o backend com o perfil local:

```bash
cd backend
SPRING_PROFILES_ACTIVE=observability-local mvn spring-boot:run
```

3. Validar:

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/prometheus
```

4. Abrir:

- Grafana: `http://localhost:3000`
- Prometheus: `http://localhost:9090`

## Cloud

1. Exportar as variáveis:

```bash
export SPRING_PROFILES_ACTIVE=observability-cloud
export OTLP_AUTHORIZATION_HEADER="Basic <base64(instance-id:api-token)>"
export OTLP_METRICS_URL="https://otlp-gateway-prod-us-central-0.grafana.net/otlp/v1/metrics"
export OTLP_TRACES_URL="https://otlp-gateway-prod-us-central-0.grafana.net/otlp/v1/traces"
export LOKI_URL="https://logs-prod-us-central1.grafana.net/loki/api/v1/push"
export LOKI_USERNAME="<loki-user>"
export LOKI_PASSWORD="<grafana-cloud-api-token>"
export OBSERVABILITY_ENVIRONMENT="production"
```

2. Subir o backend:

```bash
cd backend
mvn spring-boot:run
```

3. Validar no Grafana Cloud a chegada de métricas, traces e logs.
