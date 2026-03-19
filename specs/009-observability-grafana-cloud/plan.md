# Implementation Plan: Observabilidade com Grafana Cloud e LGTM local

**Branch**: `009-observability-grafana-cloud` | **Date**: 2026-03-18 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/009-observability-grafana-cloud/spec.md`

## Summary

Instrumentar o backend com métricas, logs estruturados e traces, usando Grafana Cloud como destino principal e uma stack LGTM self-hosted local para desenvolvimento.

## Technical Context

**Language/Version**: Java 21 / Spring Boot 3.4  
**Primary Dependencies**: Spring Boot Actuator, Micrometer Prometheus/OTLP, OpenTelemetry, Loki Logback appender  
**Storage**: Grafana Cloud (cloud) e LGTM local via Docker Compose  
**Testing**: `mvn test`, `mvn package`, smoke checks em Actuator  
**Target Platform**: backend local, Codespaces, Render, Grafana Cloud Free  
**Project Type**: backend Spring Boot com frontend separado  
**Performance Goals**: observabilidade básica sem degradar startup nem quebrar deploy  
**Constraints**: segredos fora do repositório; stack local apenas para desenvolvimento  
**Scale/Scope**: backend primeiro; frontend fica fora desta iteração

## Constitution Check

- User Value First: OK, melhora diagnósticos operacionais reais.
- Object-Oriented Design: OK, adiciona observabilidade sem romper domínio.
- Clean Architecture by Layers: OK, a instrumentação cruza camadas, mas sem misturar regra de negócio e infraestrutura além do necessário.
- Multiuser Security by Default: OK, configuração depende de variáveis de ambiente e não vaza segredos.
- Quality and Operability: OK, foco explícito em logs, métricas e traces.

## Project Structure

```text
backend/
├── pom.xml
├── src/main/resources/
│   ├── application.yml
│   └── logback-spring.xml

observability/
├── docker-compose.yml
├── prometheus/
├── grafana/
└── tempo/

specs/009-observability-grafana-cloud/
```

**Structure Decision**: manter a instrumentação no backend e criar uma pasta dedicada `observability/` para a stack local.
