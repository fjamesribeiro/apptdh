FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/api-apptdah-1.0.0.jar api-apptdah-1.0.0.jar
EXPOSE 8080
CMD [ "java", "-jar", "api-apptdah-1.0.0.jar" ]