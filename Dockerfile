# Build project and generate .jar file with maven
FROM maven:3.9.7-eclipse-temurin-22-alpine AS build


COPY . .

RUN mvn clean package

# Run java project
FROM openjdk:21-slim

WORKDIR /app

COPY --from=build  target/fernando-encurtador-de-link-0.0.1-SNAPSHOT.jar /app/app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]