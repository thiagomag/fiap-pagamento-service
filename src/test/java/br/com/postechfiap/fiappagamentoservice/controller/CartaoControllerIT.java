package br.com.postechfiap.fiappagamentoservice.controller;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext
public class CartaoControllerIT {

    @LocalServerPort
    private int port;

    @Test
    void deveCriarCartaoComSucesso() {
        // Cenário
        final var wiremock = wireMockClienteClient();

        final var cartaoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("1234567890123456")
                .dataValidade(LocalDate.now())
                .nomeTitularCartao("APRO")
                .codigoSegurancaCartao("123")
                .build();

        // Ação
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartaoRequest)
                .when()
                .post("/cartao/adicionar/1")
                .then()
                .statusCode(201);

        wiremock.stop();

    }

    @Test
    void criarCartaoDeveRetornarErro() {
        // Cenário
        final var wiremock = wireMockClienteClient();

        final var cartaoRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("1234567890123456")
                .dataValidade(LocalDate.now())
                .nomeTitularCartao("APRO")
                .codigoSegurancaCartao("123")
                .build();

        // Ação
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartaoRequest)
                .when()
                .post("/cartao/adicionar/2")
                .then()
                .statusCode(500);

        wiremock.stop();

    }

    private WireMockServer wireMockClienteClient() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        final var wireMockServer = new WireMockServer(8081);
        wireMockServer.start();

        final var clienteResponse = """
                {
                  "id": 1,
                  "nome": "Cliente Mockado",
                  "data_nascimento": "1990-01-15",
                  "cpf": "12345678900",
                  "email": "cliente.mockado@example.com",
                  "enderecos": [
                    {
                      "id": 10,
                      "cep": "12345-678",
                      "logradouro": "Rua Exemplo",
                      "numero": "100",
                      "complemento": "Apto 202",
                      "bairro": "Centro",
                      "cidade": "São Paulo",
                      "estado": "SP"
                    },
                    {
                      "id": 11,
                      "cep": "87654-321",
                      "logradouro": "Av. Teste",
                      "numero": "200",
                      "complemento": "Sala 305",
                      "bairro": "Jardins",
                      "cidade": "São Paulo",
                      "estado": "SP"
                    }
                  ]
                }
                """;

        configureFor("localhost", 8081);
        stubFor(get(urlEqualTo("/clientes/buscar/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(clienteResponse)));

        return wireMockServer;
    }
}
