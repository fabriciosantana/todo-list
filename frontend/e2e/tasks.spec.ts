import { expect, test } from '@playwright/test';
import { buildTaskSet, buildUser, createTask, registerUser } from './helpers';

test('usuario autenticado manipula tarefas em tabela, kanban e arquivadas', async ({ page }) => {
  const seed = Date.now().toString();
  const user = buildUser(seed);
  const tasks = buildTaskSet(seed);

  page.on('dialog', async (dialog) => {
    await dialog.accept();
  });

  await page.goto('/');
  await registerUser(page, user);

  await createTask(page, tasks.firstTitle, 'A_FAZER');
  await createTask(page, tasks.secondTitle, 'FAZENDO');

  const firstRow = page.locator('tr', { hasText: tasks.firstTitle });
  const secondRow = page.locator('tr', { hasText: tasks.secondTitle });

  await firstRow.getByRole('combobox').selectOption('CONCLUIDO');
  await expect(firstRow.locator('.badge')).toHaveText('Concluído');

  await page.getByTestId('view-kanban').click();

  const secondCard = page.getByTestId('kanban-column-FAZENDO').locator('[data-testid^="kanban-card-"]', {
    hasText: tasks.secondTitle
  });
  const targetColumn = page.getByTestId('kanban-column-CONCLUIDO');

  await secondCard.dragTo(targetColumn);
  await expect(page.getByTestId('kanban-column-CONCLUIDO').getByText(tasks.secondTitle)).toBeVisible();

  await page.getByTestId('view-table').click();
  await firstRow.getByTitle('Arquivar tarefa').click();
  await expect(page.getByTestId('notice-banner')).toContainText('Tarefa arquivada.');

  await page.getByTestId('scope-archived').click();
  const archivedFirstRow = page.locator('tr', { hasText: tasks.firstTitle });
  await expect(archivedFirstRow).toBeVisible();

  await archivedFirstRow.getByTitle('Desarquivar tarefa').click();
  await expect(page.getByTestId('notice-banner')).toContainText('Tarefa desarquivada.');

  await page.getByTestId('scope-active').click();
  await expect(page.locator('tr', { hasText: tasks.firstTitle })).toBeVisible();

  const activeSecondRow = page.locator('tr', { hasText: tasks.secondTitle });
  await activeSecondRow.getByTitle('Arquivar tarefa').click();
  await expect(page.getByTestId('notice-banner')).toContainText('Tarefa arquivada.');

  await page.getByTestId('scope-archived').click();
  const archivedSecondRow = page.locator('tr', { hasText: tasks.secondTitle });
  await archivedSecondRow.getByTitle('Remover tarefa permanentemente').click();
  await expect(archivedSecondRow).toHaveCount(0);
});
