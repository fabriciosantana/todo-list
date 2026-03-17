import { expect, Page } from '@playwright/test';

export interface E2eUser {
  name: string;
  email: string;
  password: string;
}

export interface E2eTaskSet {
  firstTitle: string;
  secondTitle: string;
}

export function buildUser(seed: string): E2eUser {
  return {
    name: `Usuario E2E ${seed}`,
    email: `e2e.${seed}@example.com`,
    password: '123456'
  };
}

export function buildTaskSet(seed: string): E2eTaskSet {
  return {
    firstTitle: `Planejar sprint ${seed}`,
    secondTitle: `Revisar PR ${seed}`
  };
}

export async function registerUser(page: Page, user: E2eUser): Promise<void> {
  await page.getByTestId('auth-mode-register').click();
  await page.getByTestId('auth-name').fill(user.name);
  await page.getByTestId('auth-email').fill(user.email);
  await page.getByTestId('auth-password').fill(user.password);
  await page.getByTestId('auth-submit').click();
  await expect(page.getByTestId('user-greeting')).toContainText(user.name);
}

export async function loginUser(page: Page, user: E2eUser): Promise<void> {
  await page.getByTestId('auth-mode-login').click();
  await page.getByTestId('auth-email').fill(user.email);
  await page.getByTestId('auth-password').fill(user.password);
  await page.getByTestId('auth-submit').click();
  await expect(page.getByTestId('user-greeting')).toContainText(user.name);
}

export async function createTask(page: Page, title: string, status: 'A_FAZER' | 'FAZENDO' | 'CONCLUIDO'): Promise<void> {
  await page.getByTestId('task-title-input').fill(title);
  await page.getByTestId('task-status-select').selectOption(status);
  await page.getByTestId('task-add-button').click();
  await expect(page.getByText(title)).toBeVisible();
}

export async function rowForTask(page: Page, title: string) {
  return page.locator('tr', { hasText: title });
}

export async function archivedRowForTask(page: Page, title: string) {
  return page.locator('tr', { hasText: title });
}
