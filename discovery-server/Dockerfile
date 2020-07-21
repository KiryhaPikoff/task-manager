FROM openjdk:14

WORKDIR /usr/src/discovery-server

ARG JAR_FILE=build/libs/discovery-server*.jar
COPY ${JAR_FILE} discovery-server.jar

ENTRYPOINT ["java","-jar","discovery-server.jar"]

EXPOSE 8761