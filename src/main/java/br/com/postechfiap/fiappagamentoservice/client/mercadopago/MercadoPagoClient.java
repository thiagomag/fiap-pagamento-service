package br.com.postechfiap.fiappagamentoservice.client.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCustomerResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoPaymentResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoRefundResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "Mercado Pago Client", url = "${mercado.pago.url}")
public interface MercadoPagoClient {

    @PostMapping("/v1/payments")
    MercadoPagoCustomerResponse createCustomer(MercadoPagoCreateCustomerRequest mercadoPagoCreateCustomerRequest);

    @GetMapping("/v1/customers/{customerId}")
    MercadoPagoCustomerResponse getCustomer(String customerId);

    @PutMapping("/v1/customers/{customerId}")
    MercadoPagoCustomerResponse updateCustomer(String customerId, MercadoPagoUpdateCustomerRequest mercadoPagoUpdateCustomerRequest);

    @DeleteMapping("/v1/customers/{customerId}")
    MercadoPagoCustomerResponse deleteCustomer(String customerId);

    @PostMapping("/v1/customers/{customerId}/cards")
    MercadoPagoCardResponse createCard(String customerId, MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest);

    @GetMapping("/v1/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse getCard(String customerId, String cardId);

    @DeleteMapping("/v1/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse deleteCard(String customerId, String cardId);

    @GetMapping("/v1/customers/{customerId}/cards")
    MercadoPagoCardResponse getCards(String customerId);

    @PutMapping("/v1/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse updateCard(String customerId, String cardId, MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest);

    @PostMapping("/v1/payments")
    MercadoPagoPaymentResponse createPayment(MercadoPagoCreatePaymentRequest mercadoPagoCreatePaymentRequest);

    @GetMapping("/v1/payments/{paymentId}")
    MercadoPagoPaymentResponse getPayment(String paymentId);

    @PutMapping("/v1/payments/{paymentId}")
    MercadoPagoPaymentResponse updatePayment(String paymentId, MercadoPagoUpdatePaymentRequest mercadoPagoUpdatePaymentRequest);

    @PostMapping("/v1/payments/{paymentId}/refunds")
    MercadoPagoRefundResponse refundPayment(String paymentId, MercadoPagoRefundRequest mercadoPagoRefundRequest);

    @GetMapping("/v1/payments/{paymentId}/refunds")
    MercadoPagoRefundResponse getRefunds(String paymentId);

    @GetMapping("/v1/payments/{paymentId}/refunds/{refundId}")
    MercadoPagoRefundResponse getRefund(String paymentId, String refundId);

    @PostMapping("/v1/card_tokens")
    String generateCardToken(MercadoPagoCreateCardTokenRequest mercadoPagoCreateCardTokenRequest);

}
