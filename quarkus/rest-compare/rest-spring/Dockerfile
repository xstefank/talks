FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY target/rest-spring-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
