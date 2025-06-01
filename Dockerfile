FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/SpringBatch-1.0.jar SpringBatch-1.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "SpringBatch-1.0.jar"]
