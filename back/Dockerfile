FROM adoptopenjdk/openjdk11:alpine-slim

VOLUME /tmp

ADD target/back-0.1.0.jar app.jar

RUN touch /app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]