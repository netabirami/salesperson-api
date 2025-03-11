#!/bin/bash

# Step 1: Build the latest JAR
echo "Building the latest JAR..."
mvn clean package -DskipTests

# Step 2: Build the latest Docker image and start the containers
echo "Building and running the Docker containers..."
docker-compose up --build -d
