# 🛒 E-Commerce API

The **E-Commerce API** powers the backend of an online shopping platform, enabling robust **product, order, and wishlist** management. It allows clients to create, update, delete, and query product listings with pagination and sales analytics.

The API supports secure **user-based order processing**, tracks **daily sales**, and identifies **top-selling products** through dynamic queries. Designed for **scalability and observability**, it integrates structured logging and can be visualized through the **ELK stack** for real-time monitoring.

Built using **Spring Boot**, **PostgreSQL**, and **Docker**, the system follows best practices for clean code, validation, and error handling — making it production-ready and easily extensible for real-world commerce solutions.

## 🧰 Technology Stack

- **Programming Language**: Java 21 (LTS)
- **Framework**: Spring Boot (v 3.2.2)
- **Persistence**: Spring Data JPA with Hibernate
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito
- **Documentation**: Springdoc OpenAPI (Swagger UI)
- **Logging & Monitoring**: Spring Boot Actuator, Elasticache, Logstash, Kibana 
- **Containerization**: Docker
- **Version Control**: Git

## 🛠️ Prerequisites

### To Run

- **Docker**
- **Docker Compose**

### To Develop

- **Java 21**
- **Maven 3.9+**
- **Git**
- **PostgreSQL**

#### Optional Tools

- IntelliJ / STS / VS Code
- Postman / cURL
- pgAdmin

## 🚀 Running the Application

Before running the application using either method below, first **download or clone the repository**:

```bash 
git clone https://github.com/Hafiz-SE/ecommerce-backend
cd ecommerce-backend
```

### 🐳 Containerized Mode (Recommended)

Ensure Docker and Docker Compose are installed.

1. Run the following command:

   `docker-compose up --build`

*Note: This may take longer during the first run as dependencies are downloaded. However, thanks to Docker's multi-stage
build process, subsequent runs will be significantly faster.*

2. To stop the application, press Ctrl+C in the terminal or close the terminal window.

### 🖥️ Standalone Mode (Local JVM)

1. Update the following values in the file. `src/main/resources/application-dev.properties`

- `SPRING_DATASOURCE_URL` **important:  please update the table name here also**
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

2. Run the following command in the terminal

```bash
mvn clean install
mvn clean run
```

# 👀 Viewing the Application

Once the application is running, you can access and interact with it through the following interfaces:

- **Base API URL**: [http://localhost:8080/api](http://localhost:8080/api)
- **Swagger UI (API Documentation)** : [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)
- **OpenAPI JSON Spec**: [http://localhost:8080/api/v3/api-docs](http://localhost:8080/api/v3/api-docs)
- **Health Check & Metrics (via Actuator)**:
    - **Health**: [http://localhost:8080/api/actuator/health](http://localhost:8080/api/actuator/health)
    - **Metrics**: [http://localhost:8080/api/actuator/metrics](http://localhost:8080/api/actuator/metrics)
 
**Replace `localhost:8080` with appropriate url after deployment.**  


** These endpoints validate that the application is running correctly and provide insights into its behavior and health. **

---