# Data Model: Testes de Integração do Backend com Testcontainers

## Entities

### Integration Database
- `image`: PostgreSQL usada no container
- `jdbcUrl`
- `username`
- `password`

### Authenticated Flow
- `registerRequest`
- `loginRequest`
- `jwtToken`
- `authorizedRequest`

### Repository Contract
- `ownerId`
- `archived`
- `expectedTaskCount`
