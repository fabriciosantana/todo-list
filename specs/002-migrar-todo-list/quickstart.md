# Quickstart

## 1. Subir PostgreSQL

```bash
docker compose up -d postgres
```

## 2. Rodar backend

```bash
cd backend
mvn spring-boot:run
```

Backend em `http://localhost:8080`.

## 3. Rodar frontend

```bash
cd frontend
npm install
npm start
```

Frontend em `http://localhost:4200`.

## 4. Validar fluxo

1. Cadastrar novo usuário.
2. Fazer logout e login novamente.
3. Criar, editar, concluir e remover tarefas.
4. Criar segundo usuário e validar isolamento dos dados.
