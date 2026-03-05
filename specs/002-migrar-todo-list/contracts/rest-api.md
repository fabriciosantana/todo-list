# REST API Contract

## Auth

### POST /api/auth/register

Request:
```json
{ "name": "Ana", "email": "ana@acme.com", "password": "123456" }
```

Response 200:
```json
{ "token": "jwt", "user": { "id": 1, "name": "Ana", "email": "ana@acme.com" } }
```

### POST /api/auth/login

Request:
```json
{ "email": "ana@acme.com", "password": "123456" }
```

Response 200: mesmo formato de `register`.

## Tasks (Bearer Token obrigatório)

### GET /api/tasks
Response 200:
```json
[{ "id": 10, "title": "Estudar", "completed": false, "createdAt": "...", "updatedAt": "..." }]
```

### POST /api/tasks
Request:
```json
{ "title": "Nova tarefa" }
```

### PUT /api/tasks/{id}
Request:
```json
{ "title": "Novo título", "completed": true }
```

### PATCH /api/tasks/{id}/toggle
Sem body.

### DELETE /api/tasks/{id}
Response 204.
