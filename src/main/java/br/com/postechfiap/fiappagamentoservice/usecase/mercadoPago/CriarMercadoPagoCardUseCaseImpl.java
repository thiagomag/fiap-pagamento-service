package br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago;

import br.com.postechfiap.fiappagamentoservice.adapter.MercadoPagoCardAdapter;
import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCardHolderRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreateCardRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreateCardTokenRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoIdentificationRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardTokenResponse;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoCardRepository;
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
    private final MercadoPagoCardRepository mercadoPagoCardRepository;

    @Value("${client.mercadopago.public-key}")
    private String publicKey;


    @Override
    public MercadoPagoCard execute(PagamentoContext pagamentoContext) {
        final var clienteResponse = pagamentoContext.getClienteResponse();
        final var perfilPagamentoRequest = pagamentoContext.getPerfilPagamentoRequest();
        final var mercadoPagoCustomer = pagamentoContext.getMercadoPagoCustomer();
        final var tokenResponse = mercadoPagoClient.generateCardToken(buildMercadoPagoCardTokenRequest(perfilPagamentoRequest, clienteResponse), publicKey);
        final var mercadoPagoCardResponse = mercadoPagoClient.createCard(mercadoPagoCustomer.getMercadoPagoCustomerId(), buildMercadoPagoCreateCardRequest(tokenResponse, perfilPagamentoRequest));
        final var mercadoPagoCard = mercadoPagoCardAdapter.adapt(mercadoPagoCardResponse);
        mercadoPagoCard.setMercadoPagoCustomer(pagamentoContext.getMercadoPagoCustomer());
        mercadoPagoCard.setToken(tokenResponse.getId());
        mercadoPagoCard.setPerfilPagamento(pagamentoContext.getPerfilPagamento());
        return mercadoPagoCardRepository.save(mercadoPagoCard);
    }

    private MercadoPagoCreateCardRequest buildMercadoPagoCreateCardRequest(MercadoPagoCardTokenResponse tokenResponse, PerfilPagamentoRequest perfilPagamentoRequest) {
        return MercadoPagoCreateCardRequest.builder()
                .token(tokenResponse.getId())
                .cardHolderName(perfilPagamentoRequest.getNomeTitularCartao())
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
                        .name(perfilPagamentoRequest.getNomeTitularCartao())
                        .identification(MercadoPagoIdentificationRequest.builder()
                                .type("CPF")
                                .number(customerResponse.getCpf())
                                .build())
                        .build())
                .expirationMonth(perfilPagamentoRequest.getDataValidade().getMonth().getValue())
                .expirationYear(perfilPagamentoRequest.getDataValidade().getYear())
                .securityCode(perfilPagamentoRequest.getCodigoSegurancaCartao())
                .build();
    }
}
