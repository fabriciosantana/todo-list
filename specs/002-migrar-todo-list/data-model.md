# Data Model

## User

- `id` (Long, PK)
- `name` (String, obrigatório)
- `email` (String, único, obrigatório)
- `passwordHash` (String, obrigatório)
- `role` (Enum: `ROLE_USER`)
- `createdAt` (Instant)

## Task

- `id` (Long, PK)
- `title` (String, obrigatório, max 120)
- `completed` (boolean)
- `createdAt` (Instant)
- `updatedAt` (Instant)
- `owner` (FK -> User.id)

## Relationships

- User 1 --- N Task
