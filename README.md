## Software Component Module Project - INSA
##### Ke An NGUYEN - Anh Tuan NGUYEN 
##### 5A STI Option ASL

#### Introduction
This is MVC application using Spring, Thymeleaf, h2 (in-memory database)

github: https://github.com/UNtergas/SocialMediaLite

#### Configuration
- As this is an example project, we push .env file to GitHub.
- Application configuration is in `src/main/resources/application.yml`
- The thymeleaf pages in `src/main/resources/templates` will be automatically resolved by spring boot

#### Test
We test the CRUD functionality for all models. The test is in `src/test`. You can run test using one of these options: 
1. Run with mvn `mvn test`
2. Run with `mvnw` binary
```shell
chmod +x mvnw
./mvnw test
```
3. [Easiest option] Run with intellij

#### How to run
You can run this application using one of these options:
1. Install intelliJ IDEA and click to the ide button in the top right corner (recommended)
2. Run `mvn spring-boot:run` (this command is available because we have `spring-boot-maven-plugin` as build plugin in pom.xml)
3. Run `chmod +x mvnw` and then `./mvnw spring-boot:run`

The application will be run on port 8080: http://localhost:8080

#### How to access to database
- path: http://localhost:8080/h2-console
- username: root
- password: root