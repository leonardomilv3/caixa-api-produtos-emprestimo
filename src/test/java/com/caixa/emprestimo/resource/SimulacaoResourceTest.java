package com.caixa.emprestimo.resource;

import com.caixa.emprestimo.dto.SimulacaoRequestDTO;
import com.caixa.emprestimo.entity.Produto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class SimulacaoResourceTest {

    
    // TESTES DE EXCEÇÃO


    @Test
    public void SimulacaoSemProdutoTeste() {
        SimulacaoRequestDTO request = new SimulacaoRequestDTO();
        request.setValorSolicitado(1000.0);
        request.setPrazoMeses(12);

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/api/simulacoes")
        .then()
            .statusCode(400)
            .body("erro", containsString("é obrigatório informar o idProduto"));
    }

    @Test
    public void SimulacaoProdutoInexistenteTeste() {
        SimulacaoRequestDTO request = new SimulacaoRequestDTO();
        request.setIdProduto(9999L); // Long
        request.setValorSolicitado(1000.0);
        request.setPrazoMeses(12);

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/api/simulacoes")
        .then()
            .statusCode(400)
            .body("erro", containsString("produto com id 9999 não foi encontrado"));
    }

    @Test
    public void SimulacaoValorOuPrazoInvalidosTeste() {
        // Valor inválido
        SimulacaoRequestDTO request1 = new SimulacaoRequestDTO();
        request1.setIdProduto(1L); // assumindo que o produto 1 existe
        request1.setValorSolicitado(0.0);
        request1.setPrazoMeses(12);

        given()
            .contentType(ContentType.JSON)
            .body(request1)
        .when()
            .post("/api/simulacoes")
        .then()
            .statusCode(400)
            .body("erro", containsString("valor solicitado deve ser maior que zero"));

        // Prazo inválido
        SimulacaoRequestDTO request2 = new SimulacaoRequestDTO();
        request2.setIdProduto(1L);
        request2.setValorSolicitado(1000.0);
        request2.setPrazoMeses(0);

        given()
            .contentType(ContentType.JSON)
            .body(request2)
        .when()
            .post("/api/simulacoes")
        .then()
            .statusCode(400)
            .body("erro", containsString("prazo em meses deve ser maior que zero"));
    }

    @Test
    public void SimulacaoPrazoMaiorQueProdutoTeste() {
        
        // Cadastra um produto temporário
        Produto produto = new Produto("Produto Teste Prazo", 10.0, 12);

        Integer idProduto =
            given()
                .contentType(ContentType.JSON)
                .body(produto)
            .when()
                .post("/api/produtos")
            .then()
                .statusCode(201)
                .extract().path("id");

        SimulacaoRequestDTO request = new SimulacaoRequestDTO();
        request.setIdProduto(idProduto.longValue());
        request.setValorSolicitado(1000.0);
        request.setPrazoMeses(24); 

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/api/simulacoes")
        .then()
            .statusCode(400)
            .body("erro", containsString("prazo solicitado (24 meses) excede o prazo máximo permitido (12 meses)"));
    }

    
    // TESTE DE FLUXO NORMAL
    

    @Test
    public void SimulacaoValidaTeste() {

        // Cadastra um produto
        Produto produto = new Produto("Produto Simulacao", 18.0, 24);
        Integer idProduto =
            given()
                .contentType(ContentType.JSON)
                .body(produto)
            .when()
                .post("/api/produtos")
            .then()
                .statusCode(201)
                .extract().path("id");

        // Realiza a simulação
        SimulacaoRequestDTO request = new SimulacaoRequestDTO();
        request.setIdProduto(idProduto.longValue());
        request.setValorSolicitado(1000.0);
        request.setPrazoMeses(12);

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/api/simulacoes")
        .then()
            .statusCode(200)
            .body("produto.id", equalTo(idProduto.intValue()))
            .body("valorSolicitado", equalTo(1000.0f))
            .body("prazoMeses", equalTo(12))
            .body("parcelaMensal", greaterThan(0.0f))
            .body("valorTotalComJuros", greaterThan(0.0f))
            .body("memoriaCalculo.size()", equalTo(12)); 
    }
}
