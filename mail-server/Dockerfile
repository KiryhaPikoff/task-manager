FROM openjdk:14

WORKDIR /usr/src/mail-server

ARG JAR_FILE=build/libs/mail-server*.jar
COPY ${JAR_FILE} mail-server.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar","mail-server.jar"]

EXPOSE 8080