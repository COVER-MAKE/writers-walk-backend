# Repository Guidelines

## Project Structure & Module Organization
- Gradle project (`build.gradle`, `settings.gradle`, `gradlew*`) with sources in `src/main/java/com/aivle/writerswalk`; layers map to packages: `api` (controllers), `service`, `repository`, `domain` (entities/enums), `dto` (request/response payloads), `exception` (global handler), and `config` (security, Swagger). Entry point: `WritersWalkBackendApplication.java`.
- Runtime config lives in `src/main/resources/application.yaml` (H2 in-memory DB with MySQL mode). Tests sit under `src/test/java/com/aivle/writerswalk`; assets/templates folders are currently unused.

## Build, Test, and Development Commands
- `./gradlew clean build` — compile, run all tests, and package the jar to `build/libs`.
- `./gradlew test` — execute JUnit/Spring tests only; use before pushes.
- `./gradlew bootRun` — start the API on port 8080 against the in-memory H2 DB (`/h2-console` for inspection; Swagger UI at `/swagger-ui/index.html`).

## Coding Style & Naming Conventions
- Java 17 with Spring Boot 3.5; prefer 4-space indentation and lowerCamelCase for members/methods. Keep packages aligned with the existing layer structure.
- Controllers should return `ApiResponse<?>` wrappers; DTOs end with `Request`/`Response`, entities are singular nouns, services use constructor injection (`@RequiredArgsConstructor`). Lombok is available—avoid reintroducing boilerplate.
- Security/CORS adjustments belong in `config/SecurityConfig`; document any new public endpoints.

## Testing Guidelines
- JUnit 5 via `spring-boot-starter-test`; H2 defaults give isolated runs. Name classes `*Tests` and focus on behavior over implementation details.
- For APIs, prefer `@WebMvcTest` plus `MockMvc` assertions on status and `ApiResponse` payload; use `@DataJpaTest` for repository slices and seed data per test instead of sharing state.
- Run `./gradlew test` locally before raising a PR; add regression tests whenever touching controllers, services, or security rules.

## Commit & Pull Request Guidelines
- Follow the existing convention `type: summary` (e.g., `feat: add login check`, `chore: h2 db 설정 추가`); keep the subject imperative and concise, with optional detail in the body.
- PRs should state scope, testing performed (`./gradlew test` or manual curl), linked issues, and any config impacts (CORS origins, auth flows). Include sample responses or screenshots for API-visible changes.

## Security & Configuration Tips
- Do not commit secrets; keep environment-specific overrides outside the repo. The checked-in `application.yaml` is for local/dev only.
- When opening CORS or altering auth paths, update `config/SecurityConfig` and note the expected frontend origin and session behavior in the PR description.
