# Changelog

## [Unreleased][]

[Unreleased]: https://github.com/tdevilleduc/urthehero/compare/0.3.0...HEAD

### Added

- Implement role-based access control (RBAC): add `role` field to `User` entity, enable `@EnableMethodSecurity`, restrict all write operations (PUT/POST/DELETE) to `ROLE_ADMIN` across Enemy, Page, Story and Person controllers

### Changed

- Replace deprecated OpenJDK 11 images with Eclipse Temurin 21 in `.gitlab-ci.yml`, `back/Dockerfile` and `docker-compose.yml`
- Align Kotlin `jvmTarget` from 24 to 21 (minimum required by Spring Boot 4)
- Pin PostgreSQL image to version 16 in `docker-compose.yml`
- Replace implicit `JAVA_APP_JAR` entrypoint with explicit `java -jar` command in `docker-compose.yml`

### Fixed

- Remove incorrect `test` scope from `commons-lang3` dependency (used at runtime by springdoc)

### Removed

- Remove Kubernetes deployment files (`kubernetes/`, `kubernetes-deploy.yml`, `mysql-deployment.yaml`, `secret.yaml`)
- Rewrite README to reflect actual stack and local setup (Docker Compose)

## [0.3.0][0.3.0] - 2026-03-31

[0.3.0]: https://github.com/tdevilleduc/urthehero/compare/0.2.0...0.3.0

### Added

- Add enemy/monster CRUD controller [#96][96]
- Add Swagger documentation for authentication controller
- Add JWT token parameter on Swagger UI [#92][92]
- Make all API requests authenticated [#90][90]

### Changed

- Split integration tests into separate files (with/without Spring context) [#98][98]
- Refactor services: use constants, remove HttpServletRequest, simplify code
- Fix NextPage enum mapping: add `@Enumerated(EnumType.ORDINAL)` on `Position` field
- Fix positional SQL inserts in `data.sql` to use explicit column names

### Fixed

- Remove jaxb-api dependency [#88][88]
- Fix compilation errors after Spring Boot 4 upgrade
- Add `@Configuration` to `SecurityConfig` for Spring Security 7
- Fix weak JWT signing key in tests (RFC 7518)
- Fix Spring Boot 4 compatibility: Hibernate 7 datasource initialization, circular dependencies, Resilience4j

### Upgrade

- Upgrade Spring Boot from 2.2.6 to 4.0.5
- Upgrade Spring Security to 7 (jakarta namespace)
- Upgrade SpringDoc from 1.3.3 to 3.0.1
- Upgrade TestContainers from 1.13.0 to 1.20.6
- Upgrade Resilience4j to 2.4.0 (spring-boot4)
- Upgrade Jackson from 2.10.2 to 2.12.1
- Upgrade commons-lang3 from 3.17.0 to 3.18.0
- Upgrade to Spring Cloud 2025.1.1

[88]: https://github.com/tdevilleduc/urthehero/issues/88
[90]: https://github.com/tdevilleduc/urthehero/issues/90
[92]: https://github.com/tdevilleduc/urthehero/issues/92
[96]: https://github.com/tdevilleduc/urthehero/issues/96
[98]: https://github.com/tdevilleduc/urthehero/issues/98

## [0.2.0][0.2.0] - 2020-04-15

[0.2.0]: https://github.com/tdevilleduc/urthehero//tree/0.2.0

### Added

- Add more attributes in Story entity [#21][21]
- Add docker support in local and CI/CD [#49][49]
- Add selfsigned SSL support [#51][51]
- Add DTO objects and transformation [#66][66]
- Add filter to log every incoming request [#71][71]
- Add swagger documentation [#72][72]
- Add numberOfPages attribute in story entity [#75][75]
- Add service for retrieve person by login [#76][76]
- Add authentication based on login/password from person entity [#77][77]
- Add jwt authentification support [#79][79]

[21]: https://github.com/tdevilleduc/urthehero/issues/21
[49]: https://github.com/tdevilleduc/urthehero/issues/49
[51]: https://github.com/tdevilleduc/urthehero/issues/51
[66]: https://github.com/tdevilleduc/urthehero/issues/66
[71]: https://github.com/tdevilleduc/urthehero/issues/71
[72]: https://github.com/tdevilleduc/urthehero/issues/72
[75]: https://github.com/tdevilleduc/urthehero/issues/75
[76]: https://github.com/tdevilleduc/urthehero/issues/76
[77]: https://github.com/tdevilleduc/urthehero/issues/77
[79]: https://github.com/tdevilleduc/urthehero/issues/79

### Changed

- Fix exception on startup; table 'test.page' doesn't exist [#31][31]
- Migration of application.properties to yaml [#52][52] 
- Use constants for log messages
- Migrate to postgresql database [#81][81]
- Rename person entity to user entity [#82][82]

[31]: https://github.com/tdevilleduc/urthehero/issues/31
[52]: https://github.com/tdevilleduc/urthehero/issues/52
[81]: https://github.com/tdevilleduc/urthehero/issues/81
[82]: https://github.com/tdevilleduc/urthehero/issues/82

### Upgrade

- Upgrade to spring-boot 2.2.5
- Upgrade to kotlin 1.3.70
- Upgrade to test-containers 1.13.0
- Upgrade to chaos-monkey 2.2.0
- Upgrade to resilience4j 1.3.1
- Upgrade to spring-cloud Hoxton.SR3
- Upgrade to docker.plugin 1.2.2

## [0.1.0][0.1.0] - 2019-10-31

[0.1.0]: https://github.com/tdevilleduc/urthehero//tree/0.1.0

### Added

-   Initial release
