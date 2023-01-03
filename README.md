### Dematic Task

## How It Works
CLone repository directly. It is a maven project. Preferred IDE - IntelliJ Idea. Run the DematicApplication.java class to run the project.
Once Spring server has started, use the postman link attached in your email to play around with the APIs. You can find the JAVA Doc attached in the git repo.

All POST APIs consume application/json contentType. PUT methods do request Params.

Language - JAVA Project SDK - 17 coretto. Task made as per requirements.

## Tech Stack
[Spring](https://spring.io/projects/spring-boot) for backend.

[Thymeleaf](https://www.thymeleaf.org/) for local Database.

[Postman](https://www.postman.com/) for API checking.

## Structure
The project as seen is a Sprint MVC Project which has the following packages and classes- 
### Packages
contract - Service contract for the Book Service

controller - Containing controllers for different entities

exceptions - GlobalExecption Handler and sub-package *Validator* consisting of Spring Validation API validator for Enum Class BookType.java.

model - Consists of entites of the program and a sub-package *dto* DTO classes of entities to validate them when inputing through the API

repository - CRUD repository classes for the entities

util - Utility class to check if a string is an Integer or a Double.

### Classes

Classes include both Test classes and POJO classes. Most classes define functions except the Enum Classes and Interfaces. 
The driver class is the DematicApplication.java class.

The service class defines the business logic of the application dealing with heavy duty sortings and accessing the repository for basic CRUD operatios.
The Controllers are Entity specidfic in case of adding and updating particular classes except for the BookController.java that contains other 
specific functions for retrieving all books, baarcodes, and totalprices of books in the repositories as well. 
