# Data Model

## DeploymentSecret

- `NETLIFY_AUTH_TOKEN`: token de autenticação Netlify.
- `NETLIFY_SITE_ID`: identificador do site Netlify.
- `RENDER_DEPLOY_HOOK_URL`: URL de trigger de deploy no Render.

## RuntimeEnv

- `DB_URL`: JDBC URL para PostgreSQL do Supabase.
- `DB_USERNAME`: usuário do banco Supabase.
- `DB_PASSWORD`: senha do banco Supabase.
- `JWT_SECRET`: segredo JWT em produção.
- `JWT_EXPIRATION_MS`: tempo de expiração do token.
- `CORS_ALLOWED_ORIGIN_PATTERNS`: origens permitidas no backend.
