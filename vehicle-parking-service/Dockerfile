# Use OpenJDK 22 as the base image
FROM openjdk:22-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/java_springboot_crud_example-3.0.1.jar app.jar

# Expose the application port
EXPOSE 9191

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

