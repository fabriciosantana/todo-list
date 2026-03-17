import { defineConfig, devices } from '@playwright/test';

const frontendBaseUrl = 'http://127.0.0.1:4200';
const backendBaseUrl = 'http://127.0.0.1:8080';
const jwtSecret = 'e2e-local-secret-with-adequate-length';

export default defineConfig({
  testDir: './e2e',
  fullyParallel: false,
  retries: process.env.CI ? 1 : 0,
  workers: 1,
  reporter: [['list'], ['html', { open: 'never', outputFolder: 'playwright-report' }]],
  use: {
    baseURL: frontendBaseUrl,
    trace: 'on-first-retry',
    screenshot: 'only-on-failure',
    video: 'retain-on-failure'
  },
  webServer: [
    {
      command: [
        'docker compose -f ../docker-compose.yml up -d postgres',
        'cd ../backend',
        `DB_URL=jdbc:postgresql://127.0.0.1:5432/tododb DB_USERNAME=todo DB_PASSWORD=todo JWT_SECRET=${jwtSecret} CORS_ALLOWED_ORIGIN_PATTERNS=http://127.0.0.1:4200,http://localhost:4200 mvn spring-boot:run`
      ].join(' && '),
      url: `${backendBaseUrl}/api/health`,
      timeout: 180 * 1000,
      reuseExistingServer: !process.env.CI
    },
    {
      command: `API_BASE_URL=${backendBaseUrl} npm start -- --host 127.0.0.1 --port 4200`,
      url: frontendBaseUrl,
      timeout: 120 * 1000,
      reuseExistingServer: !process.env.CI
    }
  ],
  projects: [
    {
      name: 'chromium',
      use: {
        ...devices['Desktop Chrome'],
        browserName: 'chromium'
      }
    }
  ]
});
