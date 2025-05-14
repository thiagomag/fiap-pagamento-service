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
                .status(pagamento.getStatus().getDescricao())
                .id(pagamento.getId())
                .clienteId(pagamento.getClienteId())
                .valor(pagamento.getValor())
                .pedidoId(pagamento.getPedidoId())
                .build();
    }
}
