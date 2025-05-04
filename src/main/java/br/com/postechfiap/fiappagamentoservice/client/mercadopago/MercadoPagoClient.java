package br.com.postechfiap.fiappagamentoservice.client.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.*;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "Mercado Pago Client", url = "${client.mercadopago.url}")
public interface MercadoPagoClient {

    @PostMapping("/payments")
    MercadoPagoCustomerResponse createCustomer(MercadoPagoCreateCustomerRequest mercadoPagoCreateCustomerRequest);

    @GetMapping("/customers/{customerId}")
    MercadoPagoCustomerResponse getCustomer(String customerId);

    @PutMapping("/customers/{customerId}")
    MercadoPagoCustomerResponse updateCustomer(String customerId, MercadoPagoUpdateCustomerRequest mercadoPagoUpdateCustomerRequest);

    @DeleteMapping("/customers/{customerId}")
    MercadoPagoCustomerResponse deleteCustomer(String customerId);

    @PostMapping("/customers/{customerId}/cards")
    MercadoPagoCardResponse createCard(String customerId, MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest);

    @GetMapping("/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse getCard(String customerId, String cardId);

    @DeleteMapping("/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse deleteCard(String customerId, String cardId);

    @GetMapping("/customers/{customerId}/cards")
    MercadoPagoCardResponse getCards(String customerId);

    @PutMapping("/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse updateCard(String customerId, String cardId, MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest);

    @PostMapping("/payments")
    MercadoPagoPaymentResponse createPayment(MercadoPagoCreatePaymentRequest mercadoPagoCreatePaymentRequest);

    @GetMapping("/payments/{paymentId}")
    MercadoPagoPaymentResponse getPayment(String paymentId);

    @PutMapping("/payments/{paymentId}")
    MercadoPagoPaymentResponse updatePayment(String paymentId, MercadoPagoUpdatePaymentRequest mercadoPagoUpdatePaymentRequest);

    @PostMapping("/payments/{paymentId}/refunds")
    MercadoPagoRefundResponse refundPayment(String paymentId, MercadoPagoRefundRequest mercadoPagoRefundRequest);

    @GetMapping("/payments/{paymentId}/refunds")
    MercadoPagoRefundResponse getRefunds(String paymentId);

    @GetMapping("/payments/{paymentId}/refunds/{refundId}")
    MercadoPagoRefundResponse getRefund(String paymentId, String refundId);

    @PostMapping("/card_tokens")
    MercadoPagoCardTokenResponse generateCardToken(MercadoPagoCreateCardTokenRequest mercadoPagoCreateCardTokenRequest, @PathParam("public_key") String publicKey);

}
