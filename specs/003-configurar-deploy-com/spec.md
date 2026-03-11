# Feature Specification: Deploy com GitHub Actions + Netlify + Render + Supabase

**Feature Branch**: `003-configurar-deploy-com`  
**Created**: 2026-03-05  
**Status**: Draft  
**Input**: User description: "vamos para a próxima iteração, configurar o deploy da solução usando o gitactions, sendo o frontend no netlify, o backend no render e o banco de dados no supabase."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Build e validação automática no GitHub (Priority: P1)

Como time de desenvolvimento, queremos pipeline de CI para validar frontend e backend a cada alteração.

**Why this priority**: Sem CI, o deploy pode publicar versões quebradas.

**Independent Test**: Abrir PR e verificar execução de jobs de build backend e frontend.

**Acceptance Scenarios**:

1. **Given** um push para branch com código válido, **When** o workflow de CI roda, **Then** backend e frontend compilam com sucesso.
2. **Given** código inválido em qualquer camada, **When** o workflow roda, **Then** o pipeline falha e bloqueia merge seguro.

---

### User Story 2 - Deploy automático do frontend no Netlify (Priority: P1)

Como usuário final, quero que o frontend seja publicado automaticamente no Netlify após merge na branch principal.

**Why this priority**: Interface precisa ser publicada continuamente.

**Independent Test**: Push em `main` dispara workflow que gera build Angular e publica no site Netlify.

**Acceptance Scenarios**:

1. **Given** secrets do Netlify configurados, **When** ocorre push na `main`, **Then** o workflow publica versão nova no Netlify.

---

### User Story 3 - Deploy automático do backend no Render com banco Supabase (Priority: P1)

Como usuário final, quero API publicada no Render conectada ao PostgreSQL do Supabase.

**Why this priority**: Backend e banco são obrigatórios para experiência multiusuário.

**Independent Test**: Push em `main` aciona hook de deploy do Render e API sobe com variáveis de conexão do Supabase.

**Acceptance Scenarios**:

1. **Given** deploy hook do Render configurado, **When** ocorre push na `main`, **Then** renderiza novo deploy do backend.
2. **Given** variáveis `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` apontando para Supabase, **When** backend sobe, **Then** conexão com PostgreSQL é estabelecida.

---

### Edge Cases

- Secrets ausentes no GitHub Actions devem causar falha com mensagem clara.
- Falha de deploy no Render não deve marcar sucesso silencioso.
- URL de banco incorreta deve falhar no startup do backend.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: Sistema MUST possuir workflow de CI para build/test backend e build frontend.
- **FR-002**: Sistema MUST possuir workflow de deploy do frontend para Netlify.
- **FR-003**: Sistema MUST possuir workflow de deploy do backend para Render.
- **FR-004**: Sistema MUST documentar secrets e variáveis exigidos para deploy.
- **FR-005**: Sistema MUST suportar PostgreSQL do Supabase via variáveis de ambiente.

### Key Entities *(include if feature involves data)*

- **GitHub Actions Workflow**: pipeline versionado para CI e deploy.
- **Deployment Secret**: credenciais/token necessários para publicação.
- **Environment Variables**: configuração de conexão da API com Supabase.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: CI executa com sucesso em código válido em menos de 10 minutos.
- **SC-002**: Deploy de frontend é acionado automaticamente por push na `main`.
- **SC-003**: Deploy de backend no Render é acionado automaticamente por push na `main`.
- **SC-004**: Documentação permite configurar stack de deploy sem passos implícitos.
