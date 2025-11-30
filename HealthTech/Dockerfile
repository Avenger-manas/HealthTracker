# Use Java 17 JDK
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy everything into the container
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the project using Maven wrapper
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the Spring Boot jar
CMD ["sh", "-c", "java -jar target/*.jar"]