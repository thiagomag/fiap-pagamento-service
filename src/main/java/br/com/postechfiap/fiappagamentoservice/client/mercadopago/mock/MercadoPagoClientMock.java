package br.com.postechfiap.fiappagamentoservice.client.mercadopago.mock;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Primary
@ConditionalOnProperty(name = "mock.mercado-pago.enabled", havingValue = "true")
public class MercadoPagoClientMock implements MercadoPagoClient {

    @Override
    public MercadoPagoCustomerResponse createCustomer(MercadoPagoCreateCustomerRequest req) {
        return MercadoPagoCustomerResponse.builder().build();
    }

    @Override
    public MercadoPagoCustomerResponse getCustomer(String customerId) {
        return createCustomer(null);
    }

    @Override
    public MercadoPagoCustomerResponse updateCustomer(String customerId, MercadoPagoUpdateCustomerRequest req) {
        return createCustomer(null);
    }

    @Override
    public MercadoPagoCustomerResponse deleteCustomer(String customerId) {
        return createCustomer(null);
    }

    @Override
    public MercadoPagoCardResponse createCard(String customerId, MercadoPagoCreateCardRequest req) {
        return MercadoPagoCardResponse.builder().build();
    }

    @Override
    public MercadoPagoCardResponse getCard(String customerId, String cardId) {
        return createCard(customerId, null);
    }

    @Override
    public MercadoPagoCardResponse deleteCard(String customerId, String cardId) {
        return createCard(customerId, null);
    }

    @Override
    public List<MercadoPagoCardResponse> getCards(String customerId) {
        return Collections.singletonList(createCard(customerId, null));
    }

    @Override
    public MercadoPagoCardResponse updateCard(String customerId, String cardId, MercadoPagoCreateCardRequest req) {
        return createCard(customerId, req);
    }

    @Override
    public MercadoPagoPaymentResponse createPayment(MercadoPagoCreatePaymentRequest req) {
        return MercadoPagoPaymentResponse.builder().build();
    }

    @Override
    public MercadoPagoPaymentResponse getPayment(String paymentId) {
        return createPayment(null);
    }

    @Override
    public MercadoPagoPaymentResponse updatePayment(String paymentId, MercadoPagoUpdatePaymentRequest req) {
        return createPayment(null);
    }

    @Override
    public MercadoPagoRefundResponse refundPayment(String paymentId, MercadoPagoRefundRequest req) {
        return MercadoPagoRefundResponse.builder().build();
    }

    @Override
    public List<MercadoPagoRefundResponse> getRefunds(String paymentId) {
        return Collections.singletonList(refundPayment(paymentId, null));
    }

    @Override
    public MercadoPagoRefundResponse getRefund(String paymentId, String refundId) {
        return refundPayment(paymentId, null);
    }

    @Override
    public MercadoPagoCardTokenResponse generateCardToken(MercadoPagoCreateCardTokenRequest req, String publicKey) {
        return new MercadoPagoCardTokenResponse("mock-token");
    }
}
