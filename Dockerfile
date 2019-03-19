FROM openjdk:8-jdk-alpine
MAINTAINER Erik Levi <levi.erik@gmail.com>
ADD target/security-0.0.1-SNAPSHOT.jar security.jar
ENTRYPOINT ["java", "-jar", "/security.jar"]
