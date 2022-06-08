FROM openjdk17
ARG JAR_FILE=build/libs/EnjoyRate-1.0-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]