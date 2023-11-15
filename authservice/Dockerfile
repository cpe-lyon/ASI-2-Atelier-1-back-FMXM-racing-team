# Use the official OpenJDK base image with Java 17
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/authservice-0.0.1-SNAPSHOT.jar /app/authservice.jar

# Expose the port that the application will run on
EXPOSE 8085

# Command to run the application
CMD ["java", "-jar", "authservice.jar"]