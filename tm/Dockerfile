FROM openjdk:14

WORKDIR /usr/src/task-manager

ARG JAR_FILE=build/libs/tm*.jar
COPY ${JAR_FILE} task-manager.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar","task-manager.jar"]

EXPOSE 8080