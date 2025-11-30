FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Install Maven
RUN apk add --no-cache maven bash

# Copy project files
COPY . .

# Build using Maven (skip mvnw)
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/HealthTech-0.0.1-SNAPSHOT.jar"]