FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy all files
COPY . .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build using Maven wrapper
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/HealthTech-0.0.1-SNAPSHOT.jar"]