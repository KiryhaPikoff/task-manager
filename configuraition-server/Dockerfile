FROM openjdk:14

WORKDIR /usr/src/config-server

ARG JAR_FILE=build/libs/configuraition-server*.jar
COPY ${JAR_FILE} config-server.jar

ENTRYPOINT ["java","-jar","config-server.jar"]

EXPOSE 8888