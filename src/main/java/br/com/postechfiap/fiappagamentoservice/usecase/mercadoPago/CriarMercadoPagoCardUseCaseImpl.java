package br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago;

import br.com.postechfiap.fiappagamentoservice.adapters.MercadoPagoCardAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCardHolderRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreateCardRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreateCardTokenRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoIdentificationRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardTokenResponse;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.CriarMercadoPagoCardUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarMercadoPagoCardUseCaseImpl implements CriarMercadoPagoCardUseCase {

    private final MercadoPagoClient mercadoPagoClient;
    private final MercadoPagoCardAdapter mercadoPagoCardAdapter;

    @Value("${client.mercadopago.public-key}")
    private String publicKey;


    @Override
    public MercadoPagoCard execute(PagamentoContext pagamentoContext) {
        final var customerResponse = pagamentoContext.getClienteResponse();
        final var perfilPagamentoRequest = pagamentoContext.getPerfilPagamentoRequest();
        final var mercadoPagoCustomer = pagamentoContext.getMercadoPagoCustomer();
        final var tokenResponse = mercadoPagoClient.generateCardToken(buildMercadoPagoCardTokenRequest(perfilPagamentoRequest, customerResponse), publicKey);
        final var mercadoPagoCardResponse = mercadoPagoClient.createCard(mercadoPagoCustomer.getMercadoPagoCustomerId(), buildMercadoPagoCreateCardRequest(tokenResponse));
        final var mercadoPagoCard = mercadoPagoCardAdapter.adapt(mercadoPagoCardResponse);
        mercadoPagoCard.setMercadoPagoCustomer(pagamentoContext.getMercadoPagoCustomer());
        return mercadoPagoCard;
    }

    private MercadoPagoCreateCardRequest buildMercadoPagoCreateCardRequest(MercadoPagoCardTokenResponse tokenResponse) {
        return MercadoPagoCreateCardRequest.builder()
                .token(tokenResponse.getId())
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
