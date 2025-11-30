FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build project using Maven wrapper
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Run the jar (auto pick the jar)
CMD ["java", "-jar", "target/*.jar"]