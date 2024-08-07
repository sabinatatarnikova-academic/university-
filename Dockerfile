# Use Maven image to build the project
FROM maven:3.9.8-eclipse-temurin-21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml files first to leverage Docker cache
COPY pom.xml .
COPY university-data/pom.xml university-data/
COPY university-service/pom.xml university-service/
COPY university-ui/pom.xml university-ui/

# Copy the source code
COPY university-data/src university-data/src
COPY university-service/src university-service/src
COPY university-ui/src university-ui/src

# Package the application
RUN mvn clean package -DskipTests

# Use OpenJDK image to run the application
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/university-ui/target/university-ui-0.0.1-SNAPSHOT.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
