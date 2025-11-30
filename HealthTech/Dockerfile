# ------------------------------------
# Stage 1: Build Stage (Builder)
# ------------------------------------
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper files first (This improves build caching)
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

# Make the wrapper executable (यह वह लाइन है जिसे ठीक करने की आवश्यकता थी)
RUN chmod +x mvnw

# Copy the rest of the application source code
COPY src src

# Build the application into a JAR file
# -DskipTests: avoids running tests if they are not needed for the build
RUN ./mvnw package -DskipTests

# ------------------------------------
# Stage 2: Run Stage (J-R-E only)
# ------------------------------------
# Use a smaller base image that only includes the Java Runtime Environment (JRE)
FROM eclipse-temurin:17-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot application runs on (Default is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]