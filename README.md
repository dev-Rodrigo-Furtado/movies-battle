<p align="center">
  <img src="https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg" width="320" alt="Spring Logo" />
</p>

## Test Coverage

Current project test coverage state

<img src="https://user-images.githubusercontent.com/15523493/206949822-c98a10fd-774b-4732-ad20-c6d278ea560c.png" width="320" alt="Jacoco Current Test Coverage State" />

## Warning

For while, we don't have a user subscription endpoint, so you can use this basic auth credentials for play and test this API

|     username  |    password     |
| -------------- | -------------- |
|     player01   |    102030      |
|     player02   |    102030      |

# API endpoints

### GET /api-docs 
Return a OPEN API 3.0 based response

___

### POST /quiz/start
`authorization required`

Create and start a new Quiz for the current logged user

___

### POST /quiz/{id}/stop
`authorization required`

Stop a started Quiz based on informed ID

___

### GET /quiz/{id}
`authorization required`

Return Quiz current state based on informed ID

___

### GET /quiz/{id}/question
`authorization required`

Return current Question based on informed Quiz ID

___

### POST /quiz/{id}/answer?a=FIRST
`authorization required`

Answer FIRST for the current Question based on informed Quiz ID

___

### POST /quiz/{id}/answer?a=SECOND
`authorization required`

Answer SECOND for the current Question based on informed Quiz ID

___

### GET /quiz/ranking
Return Ranking of users sorted by highest score

