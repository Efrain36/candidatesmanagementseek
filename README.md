# Candidates management app for Seek Java challenge



---

## How to use

1. **Access Swagger Documentation**

   - Local (Read "Local setup and execution with Docker" section for local execution steps) http://localhost:8080/swagger-ui/index.html#/
   - Cloud https://candidatesmanagementseek-production.up.railway.app/swagger-ui/index.html#/

2. **Create a User and Authenticate to get a JWT**

   - `/api/auth/register` Register user
   -  `/api/auth/login` Log in with the credential of your user to get a JWT

3. **Access the Application**

   Use your JWT as a Bearer token in the Authorization header for requests to the candidates API endpoints

## Local setup and execution with Docker

#### Prerequisites

1. **JDK 17**

2. **Apache Maven  3.x.x**
   
3. **Docker y Docker Compose**

#### Steps to Configure and Run Locally

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Efrain36/candidates-management-seek-challenge
   ```

2. **Set Up MySQL Database with Docker**

   ```bash
   docker-compose up -d
   ```

3. **Configure Spring Boot to Connect to the Database in `application.properties`**

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/candidates-management
   spring.datasource.username=username
   spring.datasource.password=password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```

   These settings should match the MySQL container configuration in `docker-compose.yml`.

4. **Build and Run**

   In root directory run the following commands:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
This will compile the application, run the tests, and start the Spring Boot application.


### Notes

- **Postman**: Check the project's root for Postman collection file and environment variables for local and prod testing.


