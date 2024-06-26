# Use the official OpenJDK image as a base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at /app
COPY target/Blog_App-0.0.1-SNAPSHOT.jar /app/Blog_App-0.0.1-SNAPSHOT.jar

# Expose the port that your application runs on
EXPOSE 8023

# Define the command to run your application when the container starts
CMD ["java", "-jar", "Blog_App-0.0.1-SNAPSHOT.jar"]