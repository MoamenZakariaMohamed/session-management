# Session Management

## Project Description

This project implements a timesheet tool for employees to register their login and logout times. The system allows users to log in and out, with their times stored in a MySQL database. The tool provides endpoints for user authentication and session tracking.

## Technologies Used

- **Spring Boot**: For building the application.
- **Spring Data JPA**: For database interactions.
- **Spring Security**: For securing endpoints.
- **MySQL**: As the relational database.
- **Lombok**: For reducing boilerplate code.
- **Jacoco**: For code coverage analysis.
- **JUnit & Testcontainers**: For testing, with test containers used to run integration tests in isolated environments.

## Endpoints

### Authentication Endpoints

- **Register User**
  - **URL**: `/api/v1/auth/register`
  - **Method**: `POST`
  - **Request Body**:
    ```json
    {
      "email": "string",
      "name": "string",
      "password": "string",
      "mobileNumber": "string"
    }
    ```
  - **Response**:
    ```json
    {
      "accessToken": "string"
    }
    ```
  - **Description**: Registers a new user and returns an authentication token.

- **Login User**
  - **URL**: `/api/v1/auth/login`
  - **Method**: `POST`
  - **Request Body**:
    ```json
    {
      "name": "string",
      "password": "string"
    }
    ```
  - **Response**:
    ```json
    {
      "accessToken": "string"
    }
    ```
  - **Description**: Authenticates an existing user and returns an authentication token.

### Tracking Endpoints

- **Track Session**
  - **URL**: `/api/v1/trackers`
  - **Method**: `POST`
  - **Request Body**:
    ```json
    {
      "date": "2024-08-01",
      "loginTime": "08:00:00",
      "logoutTime": "17:00:00"
    }
    ```
  - **Response**: HTTP 201 Created
  - **Description**: Records a login and logout time for a user.

- **Get Tracker by ID**
  - **URL**: `/api/v1/trackers/{id}`
  - **Method**: `GET`
  - **Response**:
    ```json
    {
      "id": 1,
      "userId": {},
      "date": "2024-08-01",
      "loginTime": "08:00:00",
      "logoutTime": "17:00:00"
    }
    ```
  - **Description**: Retrieves the tracking record for a given ID.

- **Get All Trackers**
  - **URL**: `/api/v1/trackers`
  - **Method**: `GET`
  - **Query Parameters**:
    - `start` (optional, default: 0): The starting index for pagination.
    - `count` (optional, default: 10): The number of records to retrieve.
  - **Response**:
    ```json
    [
      {
        "id": 1,
        "userId": {},
        "date": "2024-08-01",
        "loginTime": "08:00:00",
        "logoutTime": "17:00:00"
      }
    ]
    ```
  - **Description**: Retrieves a paginated list of all tracking records.

## Project Setup

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd session-management
   ```

2. **Build the Project**
   ```bash
   mvn clean install
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

## Running Tests

To run all test cases, use the following command:

```bash
mvn test
```

## Generating Code Coverage Report

To generate a code coverage report using Jacoco, execute the following command:

```bash
mvn clean test jacoco:report
```

The coverage report will be generated in `target/site/jacoco` directory. Open `index.html` in a browser to view the detailed report.

## Testing with Testcontainers

The project uses Testcontainers for integration testing. Testcontainers automatically set up the required services (e.g., MySQL) in Docker containers during tests, ensuring a consistent and isolated test environment.
