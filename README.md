# urthehero

[![Build Status](https://travis-ci.org/tdevilleduc/urthehero.svg)](https://travis-ci.org/tdevilleduc/urthehero)

```bash
@FOR /f "tokens=*" %i IN ('minikube docker-env') DO @%i
kubectl -n urthehero delete deployment back
mvn -pl back clean package dockerfile:build dockerfile:push -DskipTests -Ddocker.registry=localhost:5000
kubectl apply -f kubernetes\back.yml
kubectl -n urthehero get all
kubectl -n urthehero logs -f pod/back-5b984bf5f4-4j5kx
minikube -n urthehero service back

```
>>>
