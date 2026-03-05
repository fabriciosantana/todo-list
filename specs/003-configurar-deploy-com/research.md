# Research Notes

## Decision 1: Deploy frontend via Netlify CLI no GitHub Actions

- **Decision**: usar `npx netlify-cli deploy --prod` no workflow.
- **Rationale**: integração direta com tokens e site-id sem depender de integração externa obrigatória.
- **Alternatives considered**:
  - Netlify Git integration nativa: menos controle no repositório.

## Decision 2: Deploy backend via Render Deploy Hook

- **Decision**: acionar deploy por `curl` no deploy hook do Render.
- **Rationale**: simples, seguro via secret e desacoplado da API administrativa do Render.
- **Alternatives considered**:
  - API Render com token: mais complexidade de autenticação.

## Decision 3: Supabase como PostgreSQL gerenciado

- **Decision**: manter backend lendo `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e configurar no Render.
- **Rationale**: padrão já compatível com `application.yml`.
- **Alternatives considered**:
  - Banco local em produção: não aplicável.
