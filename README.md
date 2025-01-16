# API de Encurtamento de link

Uma API pra gerar links encurtados com Java, PostgresQL, Spring Boot e GraphQL.


## Tecnologias e ferramentas

- Java (21)
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring GraphQL
- Swagger
- PostgresQL



## Requisitos para rodar o projeto (**sem docker**)
- Ter o Java instalado na versão 21
- Ter o PostgresQL instalado na versão 17.2



## Requisitos para rodar o projeto (**com docker**)
- Ter o Java instalado na versão 21
- Ter o Docker e o Docker compose instalado



## Rodar o projeto local (**sem docker**)
### Passos
- ./scripts/run.sh


## Rodar o projeto local (**com docker**)
### Passos

- docker compose -f docker-compose.yaml up -d (**uma vez só**)
- docker compose -f docker-compose.yaml start (**quando a aplicação tenha parado e queira rodar denovo**)
- ./scripts/run.sh


## Links (localhost)

- API Rest: http://localhost:8080/
- API GraphQL: http://localhost:8080/graphql

- Documentação da API Rest: http://localhost:8080/docs/ui
- Interface gráfica do GraphQL: http://localhost:8080/graphiql


## Links

- API Rest: https://fernando-encurtador-de-link.koyeb.app/
- API GraphQL: https://fernando-encurtador-de-link.koyeb.app/graphql

- Documentação da API Rest: https://fernando-encurtador-de-link.koyeb.app/docs/ui
- Interface gráfica do GraphQL: https://fernando-encurtador-de-link.koyeb.app/graphiql
