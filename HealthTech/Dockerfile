# Base image with Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy all files to /app
COPY . .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build project using Maven wrapper
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the jar automatically (pick any jar in target)
CMD ["sh", "-c", "java -jar target/*.jar"]