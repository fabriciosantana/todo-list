# Quickstart: Melhorar Cobertura de Testes

## Backend
```bash
cd backend
mvn test jacoco:report
```

## Frontend
```bash
cd frontend
npm ci
export CHROME_BIN=$(which google-chrome || which chromium || which chromium-browser)
npm run test:ci
```

## Artifacts
- `backend/target/site/jacoco/jacoco.xml`
- `frontend/coverage/frontend/lcov.info`
