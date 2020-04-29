# urthehero

[![Build Status](https://travis-ci.org/tdevilleduc/urthehero.svg)](https://travis-ci.org/tdevilleduc/urthehero)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=urthehero&metric=coverage)](https://sonarcloud.io/dashboard?id=urthehero)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=urthehero&metric=sqale_index)](https://sonarcloud.io/dashboard?id=urthehero)

```bash
@FOR /f "tokens=*" %i IN ('minikube docker-env') DO @%i
kubectl -n urthehero delete deployment back
mvn -pl back clean package dockerfile:build dockerfile:push -DskipTests -Ddocker.registry=localhost:5000
kubectl apply -f kubernetes\back.yml
kubectl -n urthehero get all
kubectl -n urthehero logs -f pod/back-5b984bf5f4-4j5kx
```

> Construire l'image Docker

```bash
$ mvn -pl back clean package dockerfile:build dockerfile:push -DskipTests -Ddocker.registry=localhost:5000
```

> Déployer l'image sur Minikube

```bash
$ @FOR /f "tokens=*" %i IN ('minikube docker-env') DO @%i
$ mvn -pl back clean package dockerfile:build dockerfile:push -DskipTests -Ddocker.registry=localhost:5000
```
