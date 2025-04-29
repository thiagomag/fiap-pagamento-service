package br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago;

import br.com.postechfiap.fiappagamentoservice.adapters.MercadoPagoCardAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCardHolderRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreateCardRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreateCardTokenRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoIdentificationRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarMercadoPagoCardUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.CriarMercadoPagoCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarMercadoPagoCardUseCaseImpl implements CriarMercadoPagoCardUseCase {

    private final MercadoPagoClient mercadoPagoClient;
    private final MercadoPagoCardAdapter mercadoPagoCardAdapter;


    @Override
    public MercadoPagoCard execute(CriarMercadoPagoCard entry) {
        final var customerResponse = entry.getClienteResponse();
        final var perfilPagamentoRequest = entry.getPerfilPagamentoRequest();
        final var mercadoPagoCustomer = entry.getMercadoPagoCustomer();
        final var token = mercadoPagoClient.generateCardToken(buildMercadoPagoCardTokenRequest(perfilPagamentoRequest, customerResponse));
        final var mercadoPagoCardResponse = mercadoPagoClient.createCard(mercadoPagoCustomer.getMercadoPagoCustomerId(), buildMercadoPagoCreateCardRequest(token));
        final var mercadoPagoCard = mercadoPagoCardAdapter.adapt(mercadoPagoCardResponse);
        mercadoPagoCard.setMercadoPagoCustomer(entry.getMercadoPagoCustomer());
        return mercadoPagoCard;
    }

    private MercadoPagoCreateCardRequest buildMercadoPagoCreateCardRequest(String token) {
        return MercadoPagoCreateCardRequest.builder()
                .token(token)
                .build();
    }

    private MercadoPagoCreateCardTokenRequest buildMercadoPagoCardTokenRequest(PerfilPagamentoRequest perfilPagamentoRequest, ClienteResponse customerResponse) {
        if (perfilPagamentoRequest.getNumeroCartao() == null || perfilPagamentoRequest.getNumeroCartao().isEmpty()) {
            throw new IllegalArgumentException("Número do cartão não pode ser nulo ou vazio");
        }
        if (perfilPagamentoRequest.getDataValidade() == null) {
            throw new IllegalArgumentException("Data de validade não pode ser nula");
        }
        if (perfilPagamentoRequest.getCodigoSegurancaCartao() == null || perfilPagamentoRequest.getCodigoSegurancaCartao().isEmpty()) {
            throw new IllegalArgumentException("Código de segurança não pode ser nulo ou vazio");
        }
        return MercadoPagoCreateCardTokenRequest.builder()
                .cardNumber(perfilPagamentoRequest.getNumeroCartao())
                .cardHolder(MercadoPagoCardHolderRequest.builder()
                        .identification(MercadoPagoIdentificationRequest.builder()
                                .type("CPF")
                                .number(customerResponse.getCpf())
                                .build())
                        .build())
                .expirationMonth(String.valueOf(perfilPagamentoRequest.getDataValidade().getMonth()))
                .expirationYear(String.valueOf(perfilPagamentoRequest.getDataValidade().getYear()))
                .securityCode(perfilPagamentoRequest.getCodigoSegurancaCartao())
                .build();
    }
}
