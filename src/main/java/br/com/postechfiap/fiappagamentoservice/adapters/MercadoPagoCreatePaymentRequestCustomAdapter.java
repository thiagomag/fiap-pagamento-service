package br.com.postechfiap.fiappagamentoservice.adapters;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreatePaymentRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoIdentificationRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoPayerRequest;
import br.com.postechfiap.fiappagamentoservice.interfaces.CustomAdapter;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MercadoPagoCreatePaymentRequestCustomAdapter implements CustomAdapter<PagamentoContext, MercadoPagoCreatePaymentRequest> {


    @Override
    public MercadoPagoCreatePaymentRequest adapt(PagamentoContext pagamentoContext) {
        final var paymentRequest = pagamentoContext.getPagamentoRequest();
        final var mercadopagoCard = pagamentoContext.getMercadoPagoCard();
        final var clienteResponse = pagamentoContext.getClienteResponse();
        final var mercadoPagoCustomer = pagamentoContext.getMercadoPagoCustomer();

        return MercadoPagoCreatePaymentRequest.builder()
                .transactionAmount(paymentRequest.getValor())
                .token(mercadopagoCard.getToken())
                .payer(MercadoPagoPayerRequest.builder()
                        .id(mercadoPagoCustomer.getMercadoPagoCustomerId())
                        .firstName(mercadoPagoCustomer.getFirstName())
                        .lastName(mercadoPagoCustomer.getLastName())
                        .email(clienteResponse.getEmail())
                        .identification(MercadoPagoIdentificationRequest.builder()
                                .type("CPF")
                                .number(clienteResponse.getCpf())
                                .build())
                        .build())
                .externalReference(paymentRequest.getIdProduto().toString())
                .paymentMethodId(paymentRequest.getMetodoPagamento().getValue())
                .installments(paymentRequest.getParcelas())
                .metadata(Map.of("produto_id", paymentRequest.getIdProduto(),
                        "pedido_id", paymentRequest.getIdPedido(), "cliente_id", paymentRequest.getIdCliente()))
                .build();
    }
}
