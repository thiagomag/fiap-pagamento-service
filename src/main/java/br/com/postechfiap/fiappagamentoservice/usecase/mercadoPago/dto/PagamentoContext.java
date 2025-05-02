package br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto;

import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
public class PagamentoContext {

    private PagamentoRequest pagamentoRequest;
    private PerfilPagamentoRequest perfilPagamentoRequest;
    private PerfilPagamento perfilPagamento;
    private ClienteResponse clienteResponse;
    private MercadoPagoCustomer mercadoPagoCustomer;
    private MercadoPagoCard mercadoPagoCard;
    private MercadoPagoPayment mercadoPagoPayment;
    private Pagamento pagamento;
}
