# Quickstart

## 1. Configurar secrets no GitHub

No repositório GitHub, em `Settings > Secrets and variables > Actions`, criar:

- `NETLIFY_AUTH_TOKEN`
- `NETLIFY_SITE_ID`
- `RENDER_DEPLOY_HOOK_URL`

## 2. Configurar serviço backend no Render

- Criar Web Service apontando para `backend/`.
- Build command: `mvn clean package`
- Start command: `mvn spring-boot:run`
- Definir env vars:
  - `DB_URL`
  - `DB_USERNAME`
  - `DB_PASSWORD`
  - `JWT_SECRET`
  - `JWT_EXPIRATION_MS`
  - `CORS_ALLOWED_ORIGIN_PATTERNS`

## 3. Configurar frontend no Netlify

- Site criado no Netlify (manual ou existente).
- Confirmar `NETLIFY_SITE_ID` e token no GitHub secrets.

## 4. Testar pipeline

1. Fazer push para branch de trabalho e validar CI.
2. Fazer merge/push na `main`.
3. Confirmar execução de deploy frontend e backend.
4. Validar API de health e aplicação web publicada.
