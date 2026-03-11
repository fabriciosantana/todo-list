# Quickstart: Status de Tarefas com Visão Tabela e Kanban

## Backend
1. Entrar em `backend/`.
2. Rodar `mvn test`.
3. Subir a aplicação com `mvn spring-boot:run`.
4. Confirmar que o Flyway aplicou `V2__add_task_status.sql` e `V3__add_task_archiving.sql`.

## Frontend
1. Entrar em `frontend/`.
2. Rodar `npm install` se necessário.
3. Rodar `npm run build` para validação.
4. Subir localmente com `npm start` se quiser validar manualmente.

## Validação manual
1. Fazer cadastro/login.
2. Criar tarefa e confirmar status inicial `A Fazer`.
3. Trocar para a visão `Tabela` e alterar o status por seletor.
4. Trocar para a visão `Kanban`, arrastar a tarefa entre colunas e validar a mudança de status.
5. Arquivar a tarefa e confirmar que ela some da lista ativa.
6. Abrir a visão `Arquivadas`, desarquivar a tarefa e confirmar o retorno à lista ativa.
7. Arquivar novamente, tentar excluir e confirmar que o sistema pede confirmação e avisa que a ação é irreversível.
8. Disparar uma ação de sucesso e uma de erro, validar contador de 5 segundos e botão de fechar na mensagem.
