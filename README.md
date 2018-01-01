Project started using https://start.spring.io/


To verify the project vs CheckStyle rules:
    - ./gradlew clean check
    - Note that the CheckStyle rules are defined under /gradleAndBoot/config/checkstyle/checkstyle.xml.
    - To instead use CheckStyle rules at a url, see /gradleAndBoot/config/checkstyle/notes.


To build:
	- upgrade your Gradle Wrapper to the latest version 4.4.1:
		./gradlew wrapper --gradle-version=4.4.1 --distribution-type=bin
	- to build:
		./gradlew clean build


To run:
	- java -jar build/libs/gradle-and-boot-0.1.0.jar


To test:
    - curl -v -X GET http://localhost:8171/testing/getme
    200 {"createdBy":"system","description":"some description"}

    - curl -v -d '{"title":"Mr", "forename":"Philippe"}' -H "Content-Type: application/json" -X POST http://localhost:8171/testing/a1678e52-2d26-4e4d-ac25-12cccb11929b/request
    201 {"createdBy":"system","description":"some description"}

    - curl -v -d '{"some":"djiberish"}' -H "Content-Type: application/json" -X POST http://localhost:8171/testing/a1678e52-2d26-4e4d-ac25-12cccb11929b/request
    400 {"error":{"code":"VALIDATION_FAILED","timestamp":"20171228214230408","message":"Provided json fails validation."}}

    - curl -v -d '{"title":"Mr", "forename":""}' -H "Content-Type: application/json" -X POST http://localhost:8171/testing/a1678e52-2d26-4e4d-ac25-12cccb11929b/request
     400 {"error":{"code":"VALIDATION_FAILED","timestamp":"20171230210612001","message":"Provided json fails validation."}}


Notes taken while building a Docker image for this service:
    - created Dockerfile at the project root.
    - added the Palantir plugin to build.gradle.
    - to verify currently available images: docker images | grep gradle-and-boot
    - to build the tagged Docker image: ./gradlew clean build dockerTag
    - to verify nothing is running yet: docker ps
    - to start a container with the built image:
        - using the default Spring profile: docker run -p 8171:8171 -t brossierp/gradle-and-boot:0.1.0-SNAPSHOT
        - using the prod Spring profile: docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8171:8171 -t brossierp/gradle-and-boot:0.1.0-SNAPSHOT
    - to publish manually the Docker image to Docker Cloud:
        - docker login with brossierp
        - docker push brossierp/gradle-and-boot:latest
        - log into https://cloud.docker.com/ with brossierp
        - I can see my new image.

    - TODO Publish this image to Docker Cloud.