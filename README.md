## Software Component Module Project - INSA
This is MVC application using Spring, Thymeleaf, h2 (in-memory database)

#### Configuration
- As this is an example project, we push .env file to github.
- Application configuration is in `src/main/resources/application.yml`
- The thymeleaf pages in `src/main/resources/templates` will be automatically resolved by spring boot

#### Test
We test the CRUD functionality for all application models. The test is in `src/test`

#### How to run
You can run this application using one of these options:
1. Install intelliJ IDEA and click to the ide button in the top right corner (recommended)
2. Run `mvn spring-boot:run` (this command is available because we have `spring-boot-maven-plugin` as build plugin in pom.xml)

The application will be run on port 8080: http://localhost:8080

#### How to access to database
path: http://localhost:8080/h2-console
username: root
password: root