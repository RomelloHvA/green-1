# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the final lightweight image
FROM eclipse-temurin:17-jdk-alpine
# Copy the jar from the 'build' stage
COPY --from=build /target/*.jar my-app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","my-app.jar"]