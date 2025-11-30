# Base image with Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy everything to /app
COPY . .

# Install Maven inside container
RUN apk add --no-cache maven bash

# Build project using Maven
RUN mvn clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the jar automatically (pick any jar in target)
CMD ["sh", "-c", "java -jar target/*.jar"]