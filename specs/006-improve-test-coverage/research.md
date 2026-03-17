# Research: Melhorar Cobertura de Testes

## Decisions

- **Decision**: Priorizar testes unitários de serviços em vez de testes amplos de integração.
  - **Rationale**: maior ganho de cobertura e feedback rápido com menor fragilidade.
  - **Alternatives considered**: testes end-to-end no browser, rejeitados por custo e instabilidade para esta iteração.

- **Decision**: Cobrir `AuthService` e `TaskService` Angular com `HttpTestingController`.
  - **Rationale**: valida URL, método, payload e efeitos no `localStorage` com precisão.
  - **Alternatives considered**: mocks simples de `HttpClient`, rejeitados por perderem verificação do contrato HTTP.

- **Decision**: Cobrir `JwtService` diretamente com reflexão para configurar propriedades privadas.
  - **Rationale**: mantém teste unitário puro sem subir contexto Spring.
  - **Alternatives considered**: `@SpringBootTest`, rejeitado por custo desnecessário.
