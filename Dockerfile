FROM openjdk:latest
MAINTAINER Philippe Brossier <brossierp@gmail.com>
ARG JAR_FILE
ADD ${JAR_FILE} /usr/src/gradleAndBootService.jar
CMD java -jar /usr/src/gradleAndBootService.jar
