package br.com.postechfiap.fiappagamentoservice.adapters;

import br.com.postechfiap.fiappagamentoservice.controller.dto.response.PagamentoResponse;
import br.com.postechfiap.fiappagamentoservice.interfaces.CustomAdapter;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import org.springframework.stereotype.Component;

@Component
public class PagamentoResponseCustomAdapter implements CustomAdapter<PagamentoContext, PagamentoResponse> {

    @Override
    public PagamentoResponse adapt(PagamentoContext pagamentoContext) {
        final var pagamento = pagamentoContext.getPagamento();

        return PagamentoResponse.builder()
                .authorizedAt(pagamento.getAuthorizedAt())
                .status(pagamento.getStatus())
                .id(pagamento.getId())
                .capturedAt(pagamento.getCapturedAt())
                .metodoPagamento(pagamento.getMetodoPagamento())
                .clienteId(pagamento.getClienteId())
                .valor(pagamento.getValor())
                .parcelas(pagamento.getParcelas())
                .pedidoId(pagamento.getPedidoId())
                .perfilPagamentoId(pagamento.getPerfilPagamento().getId())
                .build();
    }
}
