# People Registration
This application is responsible for managing people registration.

### How to run the application
Execute the following command:

`./gradlew composeUp bootRun`

### Swagger
The API documentation is available at: 

```http://localhost:8080/api/swagger-ui.html```

### Postman Collections
Import the postman collection from: 

```../postman/people-registration.postman_collection.json```

### Actuator
| Metric | URL |
| ------------- | ------------- |
| Health check | http://localhost:8081/health |
| Liveness probes | http://localhost:8081/health/liveness |
| Readiness probes | http://localhost:8081/health/readiness |