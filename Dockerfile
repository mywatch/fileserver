FROM openjdk:8-jdk-alpine
LABEL maintainer="Test-user"
ARG JAR_FILE=target/file-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} fileServer.jar
ENTRYPOINT ["java", "-jar", "fileServer.jar"]