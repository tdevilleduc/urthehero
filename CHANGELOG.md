# Changelog

## [Unreleased][]

[Unreleased]: https://github.com/tdevilleduc/urthehero/compare/0.1.0...HEAD

### Added

- Add more attributes in Story entity [#21][21]
- Add docker support in local and CI/CD [#49][49]
- Add selfsigned SSL support [#51][51]
- Add DTO objects and transformation [#66][66]

[21]: https://github.com/tdevilleduc/urthehero/issues/21
[49]: https://github.com/tdevilleduc/urthehero/issues/49
[51]: https://github.com/tdevilleduc/urthehero/issues/51
[66]: https://github.com/tdevilleduc/urthehero/issues/66

### Changed

- Upgrade to Spring Boot 2.2.4
- Upgrade to TestContainers 1.12.5
- Upgrade to chaos-monkey 2.2.0
- Upgrade to resilience4j 1.2.0
- Upgrade to docker.plugin 1.2.2
- Fix exception on startup; table 'test.page' doesn't exist [#31][31]
- Migration of application.properties to yaml [#52][52] 
- Use constants for log messages

[31]: https://github.com/tdevilleduc/urthehero/issues/31
[52]: https://github.com/tdevilleduc/urthehero/issues/52

## [0.1.0][] - 2019-10-31

[0.1.0]: https://github.com/tdevilleduc/urthehero//tree/0.1.0

### Added

-   Initial release
