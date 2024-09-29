# Development Activity Dashboard

## Overview

The Development Activity Dashboard is a Java Spring Boot application that aggregates developer activity data from multiple GitHub repositories. It provides an API to fetch commits, pull requests, and comments for specified repositories, allowing for analysis and visualization of developer activity.

## Prerequisites

- Java 11 or higher
- Maven
- Git

## Getting Started

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone <repository-url>
cd activity-dashboard
```

### 2. Build the Application
```
mvn clean install
```

### 3. Configuration
Edit the src/main/resources/application.properties file to configure your GitHub API URL:
```
github.api.url=https://api.github.com
```

### 4. Run the Application
```
mvn spring-boot:run
```

### 5. Access the API
Once the application is running, you can access the API endpoints:

Get Developer Activities for Multiple Repositories

Endpoint: POST /users/{username}/repos/activity

Request Body:
```
[
    "repository1",
    "repository2",
    "repository3"
]
```

Example Request

```
curl -X POST \
http://localhost:8080/users/your-username/repos/activity \
-H "Content-Type: application/json" \
-d '["repo1", "repo2"]'
```

### 6. Testing the Application
```
mvn test
```