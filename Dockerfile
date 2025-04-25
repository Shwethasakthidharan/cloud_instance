# Use lightweight OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file to the container
COPY target/*.jar app.jar

# Expose the port Render will use (Render injects $PORT, but this EXPOSE is optional)
EXPOSE 8080

# Run the Spring Boot app — pick up PORT from environment
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
