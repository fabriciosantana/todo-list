# Todo List Multiusuário (Spring Boot + Angular + PostgreSQL)

Aplicação migrada para arquitetura full-stack usando:

- Backend: Java + Spring Boot + Spring Security + JPA
- Migrações de banco: Flyway
- Frontend: Angular + Bootstrap
- Banco: PostgreSQL
- Autenticação: JWT

## Estrutura

- `backend/`: API REST e regras de domínio orientadas a objetos
- `frontend/`: SPA Angular
- `specs/`: documentação spec-kit por iteração
- `.github/workflows/`: CI/CD com GitHub Actions

## Como rodar

### 1) Banco de dados

```bash
docker compose up -d postgres
```

### 2) Backend

```bash
cd backend
mvn spring-boot:run
```

API disponível em `http://localhost:8080`.
Health check disponível em `http://localhost:8080/api/health`.

As migrações do banco são aplicadas automaticamente no startup via Flyway (`backend/src/main/resources/db/migration`).

Se necessário, ajuste CORS via variável de ambiente:

```bash
export CORS_ALLOWED_ORIGIN_PATTERNS="http://localhost:4200,http://127.0.0.1:4200,https://SEU-DOMINIO.app.github.dev"
```

### 3) Frontend

```bash
cd frontend
npm install
npm start
```

Aplicação disponível em `http://localhost:4200`.

## Deploy (GitHub Actions + Netlify + Render + Supabase)

### 1) Secrets no GitHub Actions

Configurar em `Settings > Secrets and variables > Actions`:

- `NETLIFY_AUTH_TOKEN`
- `NETLIFY_SITE_ID`
- `RENDER_DEPLOY_HOOK_URL`
- `API_BASE_URL` (URL pública do backend no Render, ex.: `https://seu-backend.onrender.com`)

### 2) Frontend no Netlify

- Workflow: `.github/workflows/deploy-frontend-netlify.yml`
- Trigger: qualquer commit/merge (push) em `main`
- Build config: `netlify.toml`

### 3) Backend no Render

- Workflow: `.github/workflows/deploy-backend-render.yml`
- Trigger: qualquer commit/merge (push) em `main`
- Deploy por hook HTTP (`RENDER_DEPLOY_HOOK_URL`)

Definir no Render as variáveis:

- `DB_URL` (JDBC para Supabase, ex.: `jdbc:postgresql://db.<project>.supabase.co:5432/postgres?sslmode=require`)
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`
- `CORS_ALLOWED_ORIGIN_PATTERNS`

### 4) CI

Workflow `.github/workflows/ci.yml` valida backend e frontend em push/PR.

## Qualidade com SonarCloud

### Secrets e variables no GitHub

Configurar em `Settings > Secrets and variables > Actions`:

- Secret: `SONAR_TOKEN`
- Variable: `SONAR_ORGANIZATION`
- Variable: `SONAR_PROJECT_KEY`

### Workflow

- Workflow dedicado: `.github/workflows/sonarcloud.yml`
- Executa:
  - testes do backend com JaCoCo
  - testes do frontend em Chrome headless com LCOV
  - scanner SonarCloud

### Relatórios gerados

- Backend: `backend/target/site/jacoco/jacoco.xml`
- Frontend: `frontend/coverage/frontend/lcov.info`

### Comandos locais úteis

Backend com cobertura:

```bash
cd backend
mvn test jacoco:report
```

Frontend com cobertura:

```bash
cd frontend
npm ci
npm run test:ci
```

## API_BASE_URL no Frontend

O frontend agora usa `API_BASE_URL` no build.  
Se a variável não existir, ele faz fallback para `http://localhost:8080`.

## Migrações (Flyway)

Para evoluir o schema, crie novos arquivos versionados em:

- `backend/src/main/resources/db/migration`

Exemplo:

- `V2__add_due_date_to_tasks.sql`

## Fluxo de uso

1. Cadastrar usuário.
2. Fazer login.
3. Criar tarefa escolhendo o status inicial (`A Fazer`, `Fazendo` ou `Concluído`).
4. Alternar entre visão em tabela e visão kanban.
5. No kanban, mover tarefas entre colunas por drag and drop ou pelo seletor de status.
6. Arquivar tarefas para removê-las da lista ativa.
7. Abrir a visão `Arquivadas` para consultar e desarquivar tarefas.
8. Excluir definitivamente apenas tarefas arquivadas, com confirmação explícita.
9. Observar mensagens de sucesso/erro com fechamento manual e expiração automática em 5 segundos.
10. Fazer logout e logar com outra conta para validar isolamento multiusuário.

## Regras Funcionais Atuais

- Cada tarefa possui um status: `A_FAZER`, `FAZENDO` ou `CONCLUIDO`.
- A listagem principal trabalha apenas com tarefas ativas.
- A interface possui dois filtros de escopo: `Ativas` e `Arquivadas`.
- Tarefas arquivadas podem ser desarquivadas e voltam para a lista ativa.
- A exclusão permanente só é permitida para tarefas arquivadas.
- O kanban suporta mudança de status por arrastar e soltar.
- Feedbacks de sucesso e erro aparecem em banner temporário com contador e botão de fechar.
