FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/exchange-service-0.0.1-SNAPSHOT.jar exchange-service.jar
ENTRYPOINT ["java", "-jar","/exchange-service.jar"]