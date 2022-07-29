FROM openjdk:11
ARG JAR_FILE=build/libs/telecom-directory-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} telecom-directory-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/telecom-directory-0.0.1-SNAPSHOT.jar"]
EXPOSE  8181