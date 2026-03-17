# Research: Testes de Integração do Backend com Testcontainers

## Decisions

- **Decision**: Usar um contêiner PostgreSQL compartilhado entre testes de integração.
  - **Rationale**: reduz custo de startup sem perder fidelidade de banco real.
  - **Alternatives considered**: subir um container por classe, rejeitado por lentidão desnecessária.

- **Decision**: Usar `@SpringBootTest` + `MockMvc` para fluxos HTTP principais.
  - **Rationale**: cobre segurança, serialização, filtros e wiring sem precisar de servidor externo.
  - **Alternatives considered**: `TestRestTemplate`, rejeitado por custo maior sem ganho relevante aqui.

- **Decision**: Adicionar um teste de repositório separado com `@DataJpaTest`.
  - **Rationale**: isola comportamento JPA em banco real e mantém diagnóstico mais claro quando uma query falha.
