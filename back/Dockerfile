FROM registry.access.redhat.com/openjdk/openjdk-11-rhel7:1.0-13

ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8

VOLUME /deployments

ARG JAR_FILE

ADD target/${JAR_FILE} /deployments/app.jar

EXPOSE 8083

ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8

ENV JAVA_APP_JAR /deployments/app.jar