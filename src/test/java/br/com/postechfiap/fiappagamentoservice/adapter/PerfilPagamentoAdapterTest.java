package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerfilPagamentoAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var perfilPagamentoRequest = PerfilPagamentoRequest.builder()
                .codigoSegurancaCartao("123")
                .numeroCartao("1234567890123456")
                .nomeTitularCartao("Nome do Cartão")
                .dataValidade(LocalDate.now())
                .build();

        //when
        final var actual = new PerfilPagamentoAdapter(jsonUtils).adapt(perfilPagamentoRequest);

        //then
        assertEquals("1234", actual.getPrimeirosNumerosCartao());
        assertEquals("3456", actual.getUltimosNumerosCartao());
        assertEquals(perfilPagamentoRequest.getNomeTitularCartao(), actual.getNomeTitularCartao());
        assertEquals(perfilPagamentoRequest.getDataValidade(), actual.getDataValidade());
    }
}
