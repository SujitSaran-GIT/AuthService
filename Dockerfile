# Use official java runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/authservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java","-jar","app.jar" ]
