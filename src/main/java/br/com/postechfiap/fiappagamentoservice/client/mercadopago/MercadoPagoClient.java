package br.com.postechfiap.fiappagamentoservice.client.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardTokenResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCustomerResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoPaymentResponse;

import java.util.UUID;

public interface MercadoPagoClient {

    MercadoPagoCustomerResponse createCustomer(MercadoPagoCreateCustomerRequest mercadoPagoCreateCustomerRequest);

    MercadoPagoCustomerResponse updateCustomer(String customerId, MercadoPagoUpdateCustomerRequest mercadoPagoUpdateCustomerRequest);

    MercadoPagoCardResponse createCard(String customerId, MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest);

    MercadoPagoCardResponse deleteCard(String customerId,String cardId);

    MercadoPagoPaymentResponse createPayment(MercadoPagoCreatePaymentRequest mercadoPagoCreatePaymentRequest, UUID idempotencyId);

    MercadoPagoCardTokenResponse generateCardToken(MercadoPagoCreateCardTokenRequest mercadoPagoCreateCardTokenRequest, String publicKey);

}

