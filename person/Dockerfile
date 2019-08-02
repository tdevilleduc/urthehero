FROM openjdk:13-jdk-alpine
LABEL maintainer="devilleduc.thomas@gmail.com"
VOLUME /tmp
EXPOSE 9090
ARG JAR_FILE=target/person-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} person.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/person.jar"]
