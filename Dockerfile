FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/aws-instance-recommendation-1.0.0.jar app.jar
COPY --from=build /app/src/main/resources /app/resources
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]