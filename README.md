<p align="center">
  <img src="https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg" width="320" alt="Spring Logo" />
</p>

# API endpoints

### GET /api-docs
Return a OPEN API 3.0 based response

### GET /quiz/start
Create and start a new Quiz for the current logged user

### POST /quiz/{id}/stop
Stop a started Quiz based on informed ID

___

### GET /quiz/{id}
Return Quiz current state based on informed ID

___

### GET /quiz/{id}/question
Return current Question based on informed Quiz ID

___

### GET /quiz/{id}/answer?a=FIRST
Answer FIRST for the current Question based on informed Quiz ID

___

### GET /quiz/{id}/answer?a=SECOND
Answer SECOND for the current Question based on informed Quiz ID

___

### GET /quiz/ranking
Return Ranking of users sorted by highest score

