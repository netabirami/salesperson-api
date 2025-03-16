# Use an official OpenJDK runtime as a parent image
# Use a Maven image to build the JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /salesperson-api
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /salesperson-api

# Copy the built JAR file into the container
COPY --from=build /salesperson-api/target/*.jar salesperson-api.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "salesperson-api.jar"]
