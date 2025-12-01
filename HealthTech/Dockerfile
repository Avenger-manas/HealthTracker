# ----- Build Stage -----
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Install bash (needed for mvnw)
RUN apk add --no-cache bash

# Copy all files
COPY . .

# Give permission
RUN chmod +x mvnw

# Build jar
RUN ./mvnw clean package -DskipTests


# ----- Run Stage -----
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]