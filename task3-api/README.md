# Pet store API Test Framework

## 📌 Project Overview
This project is an API automation test framework built using **Java**, **RestAssured**, and **TestNG** to perform CRUD operations on Swagger Petstore endpoints.

---
## 🎥 Screen recording

[Video](https://drive.google.com/file/d/1XDSjIC1iMmJik6uRQg6sviWXa0wqEY10/view?usp=sharing)

[With Docker Video](https://drive.google.com/file/d/1U77tCU1JPvyZoCHhHLqBVklW1WK9tIHV/view?usp=sharing)

---
## ⚙️ Project Setup

### ✅ Prerequisites

Make sure the following tools and environments are set up on your machine:

- [JDK (Latest version)](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/download.cgi)
-  Access to endpoints
- Docker installed: [Get Docker](https://docs.docker.com/get-docker/)
- Allure Commandline: brew install allure
---

## ▶️ How to Run the Tests and View Reports
- Edit the config.properties with correct details
- Run the below command in the project root directory
```bash
mvn clean verify
```
- Run to view report
```bash
allure generate allure-results --clean -o allure-report
```
---

## 🚢 Running Tests with Docker
- Make sure Docker is running on your machine.
- Run the below command in the project root directory
```bash
docker compose up
```
---

## 🔐 Authentication
Basic Auth:
- Username: `test`
- Password: `abc123`
---

## 🧠 Why This Stack?
- **RestAssured**: Fluent, easy DSL for REST API testing.
- **Java**: Mature ecosystem, good community support, easy to onboard/hire new talent.
- **TestNG**: Powerful test runner with annotation support.
- **Allure**: Easy reporting with dashboard
---

## ⚙️ Test Scenarios Covered
- Create Pet
- Get Pet by ID
- Update Pet
- Delete Pet

---

## 🧾 Project Structure
```
petstore-api-tests/
├── config/                 # Configuration files (properties)
├── src/test/java/
│   ├── base/               # Base test setup
│   ├── config/             # ConfigManager
│   ├── models/             # Request/response POJOs
│   ├── utils/              # Auth utility
│   └── tests/              # Test classes
├── testng.xml              # TestNG suite
├── pom.xml                 # Maven config
├── allure-results/         # Reports
├── allure-history/         # Reports data
├── Dockerfile
├── run-tests.sh
├── docker-compose.yml      # docker file
└── README.md
```
---

## 👨‍💻 Maintainer
Built with ❤️ by Prasad M
---
