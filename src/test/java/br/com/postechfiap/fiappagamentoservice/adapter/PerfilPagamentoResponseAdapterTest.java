package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusBasicoEnum;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerfilPagamentoResponseAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var perfilPagamento = PerfilPagamento.builder()
                .clienteId(1L)
                .dataValidade(LocalDate.of(2030, 12, 31))
                .status(StatusBasicoEnum.ATIVO)
                .primeirosNumerosCartao("1234")
                .ultimosNumerosCartao("5678")
                .nomeTitularCartao("Nome Teste")
                .build();

        //when
        final var actual = new PerfilPagamentoResponseAdapter(jsonUtils).adapt(perfilPagamento);

        //then
        assertEquals(perfilPagamento.getId(), actual.getId());
        assertEquals(perfilPagamento.getNomeTitularCartao(), actual.getNomeTitularCartao());
        assertEquals(perfilPagamento.getDataValidade(), actual.getDataValidade());
    }
}
