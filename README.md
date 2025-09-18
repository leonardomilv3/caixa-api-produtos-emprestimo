# API de Empréstimo - Quarkus

## Como executar localmente

1. Certifique-se de ter **Java 17+** e **Maven 3.8+** instalados.
2. Clone o projeto ou extraia este ZIP.
3. Na raiz do projeto, execute:


   ```bash
   mvn quarkus:dev
   ```
   O servidor subirá em `http://localhost:8080`.

## Endpoints principais

- CRUD de produtos:
  - `GET /api/produtos`
  - `GET /api/produtos/{id}`
  - `POST /api/produtos`
  - `PUT /api/produtos/{id}`
  - `DELETE /api/produtos/{id}`

- Simulação:
  - `POST /api/simulacoes`

## Documentação Swagger

Disponível em: [http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)
