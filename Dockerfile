# Use an official OpenJDK 17 JDK slim image as the base
FROM openjdk:17-jdk-slim

# Add metadata to the image
LABEL authors="Mateusz"

# Set the working directory to /app
WORKDIR /app

# Create a volume mount point at /tmp
VOLUME /tmp

# Copy all JAR files from the target directory into the container as app.jar
COPY target/*.jar /app/App-0.0.1-SNAPSHOT.jar

# Specify the command to run on container startup
ENTRYPOINT ["java", "-jar", "/app/App-0.0.1-SNAPSHOT.jar"]