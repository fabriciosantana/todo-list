import { expect, test } from '@playwright/test';
import { buildUser, loginUser, registerUser } from './helpers';

test('cadastro e login funcionam em browser real', async ({ page }) => {
  const seed = Date.now().toString();
  const user = buildUser(seed);

  await page.goto('/');

  await expect(page.getByText('Lista de Tarefas')).toBeVisible();

  await registerUser(page, user);
  await expect(page.getByTestId('notice-banner')).toContainText('Cadastro realizado com sucesso.');

  await page.getByTestId('logout-button').click();
  await expect(page.getByTestId('notice-banner')).toContainText('Sessão encerrada.');

  await loginUser(page, user);
  await expect(page.getByTestId('notice-banner')).toContainText('Login realizado com sucesso.');
});
