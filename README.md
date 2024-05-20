# Tech Challenge - Pagameto API 
[![build](https://github.com/brunoalbrito/tech-challenge-pagamento/actions/workflows/codecov.yaml/badge.svg)](https://github.com/brunoalbrito/tech-challenge-pagamento/actions/workflows/codecov.yaml)
[![codecov](https://codecov.io/gh/brunoalbrito/tech-challenge-pagamento/graph/badge.svg?token=EI0P7UB4NN)](https://codecov.io/gh/brunoalbrito/tech-challenge-pagamento)

Esse prepositório contem a API de pagamento do Tech Challenge para o curso de Arquitetura de Software da FIAP.

Contem o microserviço responsável por proporcionar a interação com o meio de pagamento Mercado Pago, provendo assim a criação de um QR code.

## Requerimentos

- Java 21
- Postgres
- Docker

## Executando a aplicação

Na pasta root:

  ```bash
    docker compose up && ./gradlew bootRun
  ```
