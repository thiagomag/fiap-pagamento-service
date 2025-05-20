package br.com.postechfiap.fiappagamentoservice.controller;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.exception.GlobalExceptionHandler;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarPagamentoUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PagamentoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PagamentoController pagamentoController;

    @Mock
    private CriarPagamentoUseCase criarPagamentoUseCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void deveCadastrarPagamentoComSucesso() throws Exception {
        final var pagamentoRequest = PagamentoRequest.builder()
                .idCliente(1L)
                .idPedido(UUID.randomUUID())
                .skuProduto("SKU123")
                .valor(BigDecimal.TEN)
                .perfilPagamento(PerfilPagamentoRequest.builder()
                        .numeroCartao("1234567890123456")
                        .nomeTitularCartao("OTHE")
                        .codigoSegurancaCartao("123")
                        .build())
                .build();

        final var pagamentoResponse = PagamentoResponse.builder()
                .id(1L)
                .clienteId(1L)
                .valor(BigDecimal.TEN)
                .pedidoId(UUID.randomUUID().toString())
                .status(StatusPagamentoEnum.SUCESSO)
                .build();

        when(criarPagamentoUseCase.execute(pagamentoRequest))
                .thenReturn(pagamentoResponse);

        final var requestJson = new ObjectMapper().writeValueAsString(pagamentoRequest);

        mockMvc.perform(post("/pagamentos/compra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

}
