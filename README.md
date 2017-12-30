Project started using https://start.spring.io/


To verify the project vs CheckStyle rules:
    - ./gradlew clean check
    - Note that the CheckStyle rules are defined under /gradleAndBoot/config/checkstyle/checkstyle.xml.
    - TODO: what about if I want to reference online CheckStyle rules at https://raw.githubusercontent.com/ONSdigital/rm-common-config/master/checkstyle/ons-checkstyle-config.xml
            - check http://web.archive.org/web/20150322034610/http://www.gradleware.com/tutorials/feature-spotlight-enforcing-code-quality-standards/
            - and https://github.com/gradle/gradle/issues/2663


To build:
	- upgrade your Gradle Wrapper to the latest version 4.4.1:
		./gradlew wrapper --gradle-version=4.4.1 --distribution-type=bin
	- to build:
		./gradlew clean build


To run:
	- java -jar build/libs/gradleAndBoot-0.1.0.jar


To test:
    - curl -v -X GET http://localhost:8171/testing/getme
    200 {"createdBy":"system","description":"some description"}

    - curl -v -d '{"title":"Mr", "forename":"Philippe"}' -H "Content-Type: application/json" -X POST http://localhost:8171/testing/a1678e52-2d26-4e4d-ac25-12cccb11929b/request
    201 {"createdBy":"system","description":"some description"}

    - curl -v -d '{"some":"djiberish"}' -H "Content-Type: application/json" -X POST http://localhost:8171/testing/a1678e52-2d26-4e4d-ac25-12cccb11929b/request
    400 {"error":{"code":"VALIDATION_FAILED","timestamp":"20171228214230408","message":"Provided json fails validation."}}

    - curl -v -d '{"title":"Mr", "forename":""}' -H "Content-Type: application/json" -X POST http://localhost:8171/testing/a1678e52-2d26-4e4d-ac25-12cccb11929b/request
     400 {"error":{"code":"VALIDATION_FAILED","timestamp":"20171230210612001","message":"Provided json fails validation."}}


TODO: Dockerize the app
