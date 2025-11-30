# Base image with Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Install Maven (Render container may not have it)
RUN apk add --no-cache maven bash

# Copy project files
COPY . .

# Build using Maven (skip mvnw)
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/HealthTech-0.0.1-SNAPSHOT.jar"]