# Spring Redis Service
### Project Goal
This app was developed to explain redis and its features.

### Tech Stack
* Redis
* Jedis
* Java 24
* Spring Boot v3.5.5
* PostgreSQL
* Flyway
* Testcontainers
* Docker

### Run the Project
* Compile with Java 24
* Go to the project folder and run this commands
```
  $ cd spring-redis-service
  $ mvn clean install 
  $ docker build -t spring-redis-service-image .
  $ docker-compose -f docker-compose.yaml up -d
```
* If you want to add host file
```
0.0.0.0 spring-redis-service
```

### Documentation
### * [`Swagger Link`](http://localhost:8080/doc)

### Curl Commands
```
curl -X 'POST' \
  'http://localhost:8080/v1/products' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "<string>",
  "categoryId": "<integer>",
  "price": "<number>",
  "count": "<integer>",
  "description": "<string>"
}'
```
```
curl -X 'PUT' \
  'http://localhost:8080/v1/products/{{productId}}' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "<string>",
  "categoryId": "<integer>",
  "price": "<number>",
  "count": "<integer>",
  "description": "<string>"
}'
```
```
curl -X 'GET' \
  'http://localhost:8080/v1/products/{{productId}}' \
  -H 'accept: */*'
```
```
curl -X 'DELETE' \
  'http://localhost:8080/v1/products/{{productId}}' \
  -H 'accept: */*'
```
```
curl -X 'GET' \
  'http://localhost:8080/v1/categories/{{categoryId}}' \
  -H 'accept: */*'
```
```
curl -X 'DELETE' \
  'http://localhost:8080/v1/categories/{{categoryId}}' \
  -H 'accept: */*'
```
```
curl -X 'POST' \
  'http://localhost:8080/v1/categories' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "<string>"
}'
```
