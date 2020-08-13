FROM openjdk:14

WORKDIR /usr/src/task-manager-client

ARG JAR_FILE=build/libs/tm-client*.jar
COPY ${JAR_FILE} task-manager-client.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar","task-manager-client.jar"]

EXPOSE 8080