# Base image with Java 17
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

# Install Maven
RUN apk add --no-cache maven

# Build the project
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["sh", "-c", "java -jar target/*.jar"]