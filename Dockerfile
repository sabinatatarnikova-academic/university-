# Stage 1: Build the university-api module
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn -pl university-api -am clean package -DskipTests

# Stage 2: Run the built JAR
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/university-api/target/university-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
