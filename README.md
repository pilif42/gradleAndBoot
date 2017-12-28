Project started using https://start.spring.io/


To build:
	- upgrade your Gradle Wrapper to the latest version 4.4.1:
		./gradlew wrapper --gradle-version=4.4.1 --distribution-type=bin
	- to build:
		./gradlew clean build


To run:
	- java -jar build/libs/gradleAndBoot-0.1.0.jar


To test:
    - curl -v -d '{"title":"Mr", "forename":"Philippe"}' -H "Content-Type: application/json" -X POST http://localhost:8080/testing/a1678e52-2d26-4e4d-ac25-12cccb11929b/request
    201 {"createdBy":"system","description":"some description"}

    - curl -v -d '{"some":"djiberish"}' -H "Content-Type: application/json" -X POST http://localhost:8080/testing/a1678e52-2d26-4e4d-ac25-12cccb11929b/request
    TODO Should not give 201
    

TODO: Logs not coming out?
TODO: Add a unit test for the endpoint. Same for the service.
TODO: Add CheckStyle