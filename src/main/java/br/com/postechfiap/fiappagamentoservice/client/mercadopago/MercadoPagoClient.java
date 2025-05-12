package br.com.postechfiap.fiappagamentoservice.client.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.*;

import java.util.List;
import java.util.UUID;

public interface MercadoPagoClient {

    MercadoPagoCustomerResponse createCustomer(MercadoPagoCreateCustomerRequest mercadoPagoCreateCustomerRequest);

    MercadoPagoCustomerResponse getCustomer(String customerId);

    MercadoPagoCustomerResponse updateCustomer(String customerId, MercadoPagoUpdateCustomerRequest mercadoPagoUpdateCustomerRequest);

    MercadoPagoCustomerResponse deleteCustomer(String customerId);

    MercadoPagoCardResponse createCard(String customerId, MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest);

    MercadoPagoCardResponse getCard(String customerId,String cardId);

    MercadoPagoCardResponse deleteCard(String customerId,String cardId);

    List<MercadoPagoCardResponse> getCards(String customerId);

    MercadoPagoCardResponse updateCard(String customerId, String cardId, MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest);

    MercadoPagoPaymentResponse createPayment(MercadoPagoCreatePaymentRequest mercadoPagoCreatePaymentRequest, UUID idempotencyId);

    MercadoPagoPaymentResponse getPayment(String paymentId);

    MercadoPagoPaymentResponse updatePayment(String paymentId, MercadoPagoUpdatePaymentRequest mercadoPagoUpdatePaymentRequest);

    MercadoPagoRefundResponse refundPayment(String paymentId, MercadoPagoRefundRequest mercadoPagoRefundRequest);

    List<MercadoPagoRefundResponse> getRefunds(String paymentId);

    MercadoPagoRefundResponse getRefund(String paymentId, String refundId);

    MercadoPagoCardTokenResponse generateCardToken(MercadoPagoCreateCardTokenRequest mercadoPagoCreateCardTokenRequest, String publicKey);

}

