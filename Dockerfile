# Use a lightweight Java 17 runtime base image for building the application
FROM eclipse-temurin:17-jdk-jammy AS builder
ARG JAR_FILE=target/*.jar
LABEL authors="Chamod Ishankha"

# Set the working directory inside the container (using absolute path for clarity)
WORKDIR /opt/bytecub/wedahamine

# Copy the application JAR file from the host to the container
COPY ${JAR_FILE} wedahamine_backend.jar

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Set the default command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "wedahamine_backend.jar"]