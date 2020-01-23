# SonarQube

* Start Docker
* Start SonarQube container
```
docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube:7.9.2-community
```

* Launch Sonar export
```
mvn clean package sonar:sonar
```

* Access to Sonar UI

http://localhost:9000/

* Authenticate
#### login
```
admin
```

#### password
```
admin
```
