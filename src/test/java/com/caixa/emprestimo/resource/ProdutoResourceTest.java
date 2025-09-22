package com.caixa.emprestimo.resource;

import com.caixa.emprestimo.entity.Produto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProdutoResourceTest {


    // TESTES DE EXCEÇÃO (BAD REQUEST)

    @Test
    public void CadastraProdutoSemNomeTeste() {
        Produto produto = new Produto();
        produto.taxaJurosAnual = 15.0;
        produto.prazoMaximoMeses = 12;

        given()
            .contentType(ContentType.JSON)
            .body(produto)
        .when()
            .post("/api/produtos")
        .then()
            .statusCode(400)
            .body("erro", containsString("nome do produto é obrigatório"));
    }

    @Test
    public void CadastraProdutoSemTaxaJurosTeste() {
        Produto produto = new Produto();
        produto.nome = "Produto Sem Taxa";
        produto.prazoMaximoMeses = 12;

        given()
            .contentType(ContentType.JSON)
            .body(produto)
        .when()
            .post("/api/produtos")
        .then()
            .statusCode(400)
            .body("erro", containsString("taxa de juros anual deve ser maior que zero"));
    } 

    @Test
    public void CadastraProdutoSemPrazoTeste() {
        Produto produto = new Produto();
        produto.nome = "Produto Sem Prazo";
        produto.taxaJurosAnual = 15.0;

        given()
            .contentType(ContentType.JSON)
            .body(produto)
        .when()
            .post("/api/produtos")
        .then()
            .statusCode(400)
            .body("erro", containsString("prazo máximo deve ser maior que zero"));
    }

    @Test
    public void BuscaProdutoInexistenteTeste() {
        given()
            .when()
            .get("/api/produtos/9999")
        .then()
            .statusCode(400)
            .body("erro", containsString("não foi encontrado no sistema"));
    }

    @Test
    public void AtualizaProdutoInexistenteTeste() {
        Produto produto = new Produto();
        produto.nome = "Produto Teste";
        produto.taxaJurosAnual = 12.0;
        produto.prazoMaximoMeses = 10;

        given()
            .contentType(ContentType.JSON)
            .body(produto)
        .when()
            .put("/api/produtos/9999")
        .then()
            .statusCode(400)
            .body("erro", containsString("não foi encontrado no sistema"));
    }

    @Test
    public void DeletaProdutoInexistenteTeste() {
        given()
            .when()
            .delete("/api/produtos/9999")
        .then()
            .statusCode(400)
            .body("erro", containsString("não foi encontrado no sistema"));
    }

    

    // TESTES DE FLUXO COMPLETO (CRUD)
  


    @Test
    public void CadastraProdutoTest(){
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setTaxaJurosAnual(15.0);
        produto.setPrazoMaximoMeses(12);

        given()
            .contentType(ContentType.JSON)
            .body(produto)
        .when()
            .post("/api/produtos")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", equalTo("Produto Teste"))
            .body("taxaJurosAnual", equalTo(15.0f))
            .body("prazoMaximoMeses", equalTo(12));
    }

    @Test
    public void BuscaProdutosCadastradosTeste() {
        // Cria 3 produtos
        given()
            .contentType(ContentType.JSON)
            .body(new Produto("Produto 1", 10.0, 12))
        .when()
            .post("/api/produtos")
        .then()
            .statusCode(201);

        given()
            .contentType(ContentType.JSON)
            .body(new Produto("Produto 2", 12.5, 24))
        .when()
            .post("/api/produtos")
        .then()
            .statusCode(201);

        given()
            .contentType(ContentType.JSON)
            .body(new Produto("Produto 3", 15.0, 36))
        .when()
            .post("/api/produtos")
        .then()
            .statusCode(201);

        // Verifica se existem pelo menos 3 produtos cadastrados
        given()
            .when()
            .get("/api/produtos")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(3));
    }


    @Test
    public void AtualizaProdutoTeste() {
        // 1. Cadastra produto
        Integer id =
            given()
                .contentType(ContentType.JSON)
                .body(new Produto("Produto Antigo", 10.0, 12))
            .when()
                .post("/api/produtos")
            .then()
                .statusCode(201)
                .body("id", notNullValue())
                .extract().path("id");

        // 2. Atualiza produto
        Produto atualizado = new Produto();
        atualizado.setNome("Produto Atualizado");
        atualizado.setTaxaJurosAnual(20.0);
        atualizado.setPrazoMaximoMeses(36);

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
        .when()
            .put("/api/produtos/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("nome", equalTo("Produto Atualizado"))
            .body("taxaJurosAnual", equalTo(20.0f))
            .body("prazoMaximoMeses", equalTo(36));
    }

    @Test
    public void DeletaProdutoCadastradoTeste() {
        // 1. Cadastra produto
        Integer id =
            given()
                .contentType(ContentType.JSON)
                .body(new Produto("Produto Teste", 18.0, 24))
            .when()
                .post("/api/produtos")
            .then()
                .statusCode(201)
                .body("id", notNullValue())
                .extract().path("id");

        // 2. Confirma que existe
        given()
            .when()
            .get("/api/produtos/" + id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("nome", equalTo("Produto Teste"));
        
        // 3. Deleta produto
        given()
            .when()
            .delete("/api/produtos/" + id)
        .then()
            .statusCode(200)
            .body(containsString("Produto removido com sucesso!"));

        // 4. Confirma que não existe mais
        given()
            .when()
            .get("/api/produtos/" + id)
        .then()
            .statusCode(400)
            .body("erro", containsString("não foi encontrado no sistema"));
    }
}
