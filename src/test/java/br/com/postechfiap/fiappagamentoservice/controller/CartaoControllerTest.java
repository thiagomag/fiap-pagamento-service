package br.com.postechfiap.fiappagamentoservice.controller;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PerfilPagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.exception.GlobalExceptionHandler;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.AdicionarCartaoUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.DeletarCartaoClienteUseCase;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.ListarCartoesClienteUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartaoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CartaoController cartaoController;

    @Mock
    private AdicionarCartaoUseCase adicionarCartaoUseCase;
    @Mock
    private ListarCartoesClienteUseCase listarCartoesClienteUseCase;
    @Mock
    private DeletarCartaoClienteUseCase deletarCartaoClienteUseCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartaoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void deveAdicionarCartaoComSucesso() throws Exception {
        final var perfiPagamentpRequest = PerfilPagamentoRequest.builder()
                .numeroCartao("123456")
                .nomeTitularCartao("Nome Teste")
                .codigoSegurancaCartao("123")
                .build();
        final var perfilPagamentoResponse = PerfilPagamentoResponse.builder()
                .id(1L)
                .primeirosNumerosCartao("1234")
                .ultimosNumerosCartao("5678")
                .clienteId(1L)
                .dataValidade(LocalDate.now())
                .nomeTitularCartao("Nome Teste")
                .build();

        when(adicionarCartaoUseCase.execute(perfiPagamentpRequest))
                .thenReturn(perfilPagamentoResponse);

        final var requestJson = new ObjectMapper().writeValueAsString(perfiPagamentpRequest);

        mockMvc.perform(post("/cartao/adicionar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    void deveListarCartoesComSucesso() throws Exception {
        final var perfilPagamentoResponse = PerfilPagamentoResponse.builder()
                .id(1L)
                .primeirosNumerosCartao("1234")
                .ultimosNumerosCartao("5678")
                .clienteId(1L)
                .dataValidade(LocalDate.now())
                .nomeTitularCartao("Nome Teste")
                .build();

        when(listarCartoesClienteUseCase.execute(1L))
                .thenReturn(List.of(perfilPagamentoResponse));

        mockMvc.perform(get("/cartao/listar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].cliente_id").value(1L));
    }

    @Test
    void deveDeletarCartaoComSucesso() throws Exception {
        final var perfilPagamentoReques = 1L;

        doNothing().when(deletarCartaoClienteUseCase).execute(perfilPagamentoReques);

        mockMvc.perform(delete("/cartao/deletar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
