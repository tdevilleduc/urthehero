# Changelog

## [Unreleased][]

[Unreleased]: https://github.com/tdevilleduc/urthehero/compare/0.2.0...HEAD

### Added


### Changed


### Upgrade

- Upgrade to Spring Boot 2.3.0
- Upgrade to springdoc 1.4.0
- Upgrade to test-containers 1.14.3


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
