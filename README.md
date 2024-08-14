# Cheese Central API
## Description

Project made with the purpose of getting more knowledge in the Spring framework and Java in general. In this API, you can manage products, categories, accounts, carts and orders.

### Pre-requisites
* Java 17
* PostgreSQL
* IDE of your choice

### Installation
1. Clone the project or download it by github
```bash
git clone https://github.com/joaosantosmedeiros/cheese-central-spring
```

2. Create a PostgreSQL database for the project
3. Insert your PostgreSQL credentials in `application.properties`
4. Install the dependencies
```bash
mvn clean install -DskipTests
```
5. Run the app

### Docs
All the documentation can be found at http://localhost:8080/swagger-ui.html

### Used technologies
<img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" />
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white" />
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white">
<img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white">

### Authors
* **João Pedro dos Santos Medeiros** - Backend Developer - [@joaosantosmedeiros](https://github.com/joaosantosmedeiros)

### Contact
* [LinkedIn](https://www.linkedin.com/in/joao-pedro-dos-santos-medeiros)
* <jopesame@gmail.com>

### Next Steps
For the next steps, i'm planning to:
* Add a RabbitMQ connection with another API.
* Add integration tests.
* Dockerize both created APIs, RabbitMQ and Postgres.