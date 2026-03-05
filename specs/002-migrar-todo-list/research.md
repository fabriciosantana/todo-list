# Research Notes

## Decision 1: JWT stateless para autenticação

- **Decision**: Autenticação por token JWT no backend Spring Security.
- **Rationale**: Simplifica integração com SPA Angular e escala horizontal.
- **Alternatives considered**:
  - Sessão servidor: maior acoplamento de estado.
  - OAuth completo: overhead para MVP.

## Decision 2: User-Task relação 1:N no domínio

- **Decision**: Cada `Task` pertence a um único `User`.
- **Rationale**: Atende requisito multiusuário com isolamento claro.
- **Alternatives considered**:
  - Tarefas compartilhadas: fora do escopo inicial.

## Decision 3: Angular standalone + Bootstrap

- **Decision**: Interface Angular em componente standalone com Bootstrap.
- **Rationale**: Agilidade de entrega e UI responsiva consistente.
- **Alternatives considered**:
  - CSS custom completo: maior custo inicial.
