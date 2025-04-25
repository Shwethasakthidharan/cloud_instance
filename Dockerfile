# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Install Maven (so we can build the Java project)
RUN apt-get update && apt-get install -y maven

# Set the working directory in the container
WORKDIR /app

# Copy the entire project into the container
COPY . /app

# Build the application using Maven (this will generate the .jar)
RUN mvn clean package

# Expose the port that Render will use (Render injects $PORT)
EXPOSE 8080

# Run the Spring Boot app (assuming the jar is in target/)
ENTRYPOINT ["java", "-jar", "target/*.jar"]
