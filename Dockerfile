FROM openjdk:11-jre-slim

WORKDIR /opwatch-server
VOLUME /opwatch-server/resources
COPY ./build/libs/*.jar app.jar
COPY ./build/resources/main/* resources/*
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/ircBot/app.jar"]
