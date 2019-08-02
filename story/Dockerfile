FROM openjdk:13-jdk-alpine
LABEL maintainer="devilleduc.thomas@gmail.com"
VOLUME /tmp
EXPOSE 9091
ARG JAR_FILE=target/story-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} story.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/story.jar"]

