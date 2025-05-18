package br.com.postechfiap.fiappagamentoservice.controller;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext
public class PagamentoControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
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
    }

    @Test
    void deveCriarPagamentoComSucesso() {
        // Cenário
        final var pagamentoRequest = PagamentoRequest.builder()
                .idCliente(1L)
                .idPedido(UUID.randomUUID())
                .skuProduto("SKU123")
                .valor(BigDecimal.TEN)
                .perfilPagamento(PerfilPagamentoRequest.builder()
                        .numeroCartao("1234567890123456")
                        .dataValidade(LocalDate.now())
                        .nomeTitularCartao("APRO")
                        .codigoSegurancaCartao("123")
                        .build())
                .build();

        // Ação
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest)
                .when()
                .post("/pagamentos/compra")
                .then()
                .statusCode(200);
    }

    @Test
    void deveCriarPagamentoComErro() {
        // Cenário
        final var pagamentoRequest = PagamentoRequest.builder()
                .idCliente(1L)
                .idPedido(UUID.randomUUID())
                .skuProduto("SKU123")
                .valor(BigDecimal.TEN)
                .perfilPagamento(PerfilPagamentoRequest.builder()
                        .numeroCartao("1234567890123456")
                        .dataValidade(LocalDate.now())
                        .nomeTitularCartao("OTHE")
                        .codigoSegurancaCartao("123")
                        .build())
                .build();

        // Ação
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest)
                .when()
                .post("/pagamentos/compra")
                .then()
                .statusCode(400)
                .assertThat()
                .onFailMessage("Pagamento com o id 4 rejeitado: FALHA");
    }
}
