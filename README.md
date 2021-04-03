# spring-boot-api (Product catalog)
Rest api  for Product catalog with Java and Spring Boot.

## Api Technologies


- Java 11
- Spring Framework
- Maven 4
- jUnit 5
- H2 database ( In-memory database )
- Docker

## How to run

You can clone this repository then open the project in your favorite IDE (i developed and recommend Spring Tool Suite 4(https://spring.io/tools)).

Go to Run as > Maven build... > type 'clean package' in Goals input and click in RUN.

Then go to target folder and execute following command in your terminal:

```
java -jar spring-boot-api-1.0.0.jar
```
This project was configured to run on localhost on 9999 port, as http://localhost:9999/

## About the API

### JSON format

This is the expected format for all interactions with this api

```javascript
  {
    "id": "string",
    "name": "string",
    "description": "string",
    "price": 59.99
  }
```

### Endpoints

| HTTP Verbs  |  Resource path    |           Description         |
|-------------|:-----------------:|------------------------------:|
| POST        |  /products        |   Update a product            |
| PUT         |  /products/{id}   |   Update a product            |
| GET         |  /products/{id}   |   Search a product by ID      |
| GET         |  /products        |   List of products            |
| GET         |  /products/search |   List of filtered products   |
| DELETE      |  /products/{id}   |   Delete a product by ID      |

## Improvements made

### Docker

- 02/04/21: Created dockerfile - now running ```mvn package``` you build a fatjar and generate a docker image. 
  
  After build, run
  
  ```docker images```
  
  and see a new image with data that can be change in pom file.
  
  Then, run
  ```docker run -p 9999:9999 {image name}```
  
  All done! The app are running at docker!
  

## Improvements for the future 

- Persist all data in a no-sql database as mongoDB
- Apply docker containers
- Do more and better unit tests
