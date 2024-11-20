# Shopping App
Customers can add products to their cart, place orders, and view their order history. Cart and order totals are calculated dynamically, and product stock is tracked.

## Requirements

- Docker
- Java 17+
- Maven

### Running with Docker

1. Install Docker and Docker Compose. (For installation, visit the [Docker Docs](https://docs.docker.com/get-docker/))
2. In the project directory, run the following command to start all containers:

    ```bash
    docker-compose up -d
    ```

3. If you only want to start the database container, use the following command:

    ```bash
    docker-compose up -d java_database
    ```

### Running with Maven

1. Clean the project and install dependencies:

    ```bash
    mvn clean install
    ```

2. Run the Spring Boot application:

    ```bash
    mvn spring-boot:run
    ```

Once the project is running, you can access the application at `http://localhost:8080`.



