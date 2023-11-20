# Stage 1: Build the application with Gradle
FROM gradle:8.4.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Stage 2: Run the application with Java
FROM eclipse-temurin:17-jre-alpine
LABEL version="1.0"
LABEL description="This is the base docker image for the Spring Boot Application (Demo Bookshop Service)"
EXPOSE 8080
RUN mkdir /app
# Create a group and user to run the application
RUN addgroup -S spring && adduser -S spring -G spring
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
RUN chown spring:spring /app/spring-boot-application.jar
USER spring:spring
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]