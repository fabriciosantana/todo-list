# Contract: Integration Scope

## Repository Integration
- validar `TaskRepository.findAllByOwnerIdAndArchivedOrderByCreatedAtDesc`
- validar `TaskRepository.findByIdAndOwnerId`
- validar `UserRepository.findByEmail` e `existsByEmail`

## HTTP Integration
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/tasks`
- `POST /api/tasks`
- `PATCH /api/tasks/{id}/archive`
- `PATCH /api/tasks/{id}/unarchive`
