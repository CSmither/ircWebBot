FROM openjdk:11-jre-slim

WORKDIR /ircWebBot
VOLUME /ircWebBot/resources
COPY ./build/libs/*.jar app.jar
COPY ./build/resources/main/* resources/*
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/ircWebBot/app.jar"]
