# Deployment Contract

## CI Workflow

- Trigger: `push` e `pull_request`.
- Deve validar build backend e frontend.

## Frontend Deploy Workflow

- Trigger: `push` na `main`.
- Pré-condição: secrets `NETLIFY_AUTH_TOKEN` e `NETLIFY_SITE_ID` configurados.
- Resultado esperado: deploy em produção do frontend no Netlify.

## Backend Deploy Workflow

- Trigger: `push` na `main`.
- Pré-condição: secret `RENDER_DEPLOY_HOOK_URL` configurado.
- Resultado esperado: chamada HTTP 2xx aciona deploy no Render.
