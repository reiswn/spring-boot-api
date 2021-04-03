FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/spring-boot-api.jar
WORKDIR /app
ENTRYPOINT java -jar spring-boot-api.jar