# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and the gradle build files
COPY gradlew .
COPY gradle gradle

# Copy the project source code
COPY src src
COPY build.gradle settings.gradle ./

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build

# Use a smaller base image for the final image
FROM eclipse-temurin:21-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
