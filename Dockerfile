# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /salesperson-app

# Copy the built JAR file into the container
COPY target/*.jar salesperson-app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "salesperson-app.jar"]
