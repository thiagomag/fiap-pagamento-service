package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.entities.Pagamento;
import br.com.postechfiap.fiappagamentoservice.entities.PerfilPagamento;
import br.com.postechfiap.fiappagamentoservice.enuns.MetodoPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PagamentoResponseCustomAdapterTest {

    @Test
    void adaptShouldReturnSuccessfully() {
        //given
        final var pagamento = Pagamento.builder()
                .id(1L)
                .clienteId(123L)
                .valor(BigDecimal.TEN)
                .status(StatusPagamentoEnum.SUCESSO)
                .parcelas(1)
                .pedidoId("123456")
                .metodoPagamento(MetodoPagamentoEnum.CARTAO_CREDITO)
                .perfilPagamento(PerfilPagamento.builder().build())
                .build();
        final var pagamentoContext = PagamentoContext.builder()
                .pagamento(pagamento)
                .build();

        //when
        final var actual = new PagamentoResponseCustomAdapter().adapt(pagamentoContext);

        //then
        assertNotNull(actual);
        assertEquals(StatusPagamentoEnum.SUCESSO, actual.getStatus());
        assertEquals(pagamento.getValor(), actual.getValor());
        assertEquals(pagamento.getPedidoId(), actual.getPedidoId());
        assertEquals(pagamento.getClienteId(), actual.getClienteId());
    }
}
