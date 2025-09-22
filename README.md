# API de Simulação de Empréstimo


Este projeto é uma aplicação desenvolvida em **Java com Quarkus**, utilizando banco **H2 em memória** para persistência, integração com **Swagger (OpenAPI)** para documentação automática da API e suporte a variáveis de ambiente via `.env`.

---

## Requisitos

- **Java 17+**
- **Maven 3.8+**
- **Git**
- Arquivo `.env` na raiz do projeto



---

## Variáveis de Ambiente

O projeto utiliza variáveis de ambiente para configurar o **usuário** e a **senha** do banco de dados.

### Arquivo `.env`

Crie um arquivo `.env` na raiz do projeto:

```env
DB_USER=<criar_user_db>
DB_PASSWORD=<criar_passaword_db>
```


## Como executar localmente

1. Certifique-se de ter **Java 17+** e **Maven 3.8+** instalados.
2. Extraia o ZIP do projeto.
3. Crie o arquivo `.env` na raiz do projeto, com usuario (DB_USER) e senha (DB_PASSWORD), se já não tiver criado.
4. Exporte as variáveis do arquivo `.env`:

No Linux:

```bash
export $(cat .env | xargs)
```

No Windows:

```bash
$env:DB_USER="user_banco"
$env:DB_PASSWORD="password_banco"
```


5. Na raiz do projeto, execute:


```bash
mvn compile
mvn quarkus:dev
```

O servidor subirá em `http://localhost:8080`.

6. Acessando o Swagger / OpenAPI:

Após subir o projeto em modo dev, acesse:

- **Swagger UI**: http://localhost:8080/q/swagger-ui/
- Esquema OpenAPI: http://localhost:8080/openapi


## Como realizar o buid da API


```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```


## Endpoints principais (documentados no Swagger)

- CRUD de produtos:
  - `GET /api/produtos`
  - `GET /api/produtos/{id}`
  - `POST /api/produtos`
  - `PUT /api/produtos/{id}`
  - `DELETE /api/produtos/{id}`

- Simulação:
  - `POST /api/simulacoes`

