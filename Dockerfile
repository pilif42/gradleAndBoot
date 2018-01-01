FROM openjdk:latest
MAINTAINER Philippe Brossier <brossierp@gmail.com>
ARG JAR_FILE
COPY target/${JAR_FILE} /usr/src/gradleAndBootService.jar
CMD java -jar /usr/src/gradleAndBootService.jar
