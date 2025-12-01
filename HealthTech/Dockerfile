FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy project files
COPY . .

# Install bash (needed for mvnw script)
RUN apk add --no-cache bash

# Give executable permission to mvnw
RUN chmod +x mvnw

# Build Spring Boot app
RUN ./mvnw clean package -DskipTests

# Expose app port
EXPOSE 8080

# Run the generated jar
CMD ["java", "-jar", "target/HealthTech-0.0.1-SNAPSHOT.jar"]