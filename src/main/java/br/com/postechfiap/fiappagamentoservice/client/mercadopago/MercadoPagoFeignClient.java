package br.com.postechfiap.fiappagamentoservice.client.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "MercadoPagoClient", url = "${client.mercadopago.url}")
@ConditionalOnProperty(name = "mock.mercado-pago.enabled", havingValue = "false", matchIfMissing = true)
public interface MercadoPagoFeignClient extends MercadoPagoClient {

    // CUSTOMERS

    @PostMapping("/customers")
    MercadoPagoCustomerResponse createCustomer(@RequestBody MercadoPagoCreateCustomerRequest mercadoPagoCreateCustomerRequest);

    @GetMapping("/customers/{customerId}")
    MercadoPagoCustomerResponse getCustomer(@PathVariable("customerId") String customerId);

    @PutMapping("/customers/{customerId}")
    MercadoPagoCustomerResponse updateCustomer(
            @PathVariable("customerId") String customerId,
            @RequestBody MercadoPagoUpdateCustomerRequest mercadoPagoUpdateCustomerRequest
    );

    @DeleteMapping("/customers/{customerId}")
    MercadoPagoCustomerResponse deleteCustomer(@PathVariable("customerId") String customerId);


    // CARDS

    @PostMapping("/customers/{customerId}/cards")
    MercadoPagoCardResponse createCard(
            @PathVariable("customerId") String customerId,
            @RequestBody MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest
    );

    @GetMapping("/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse getCard(
            @PathVariable("customerId") String customerId,
            @PathVariable("cardId") String cardId
    );

    @DeleteMapping("/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse deleteCard(
            @PathVariable("customerId") String customerId,
            @PathVariable("cardId") String cardId
    );

    @GetMapping("/customers/{customerId}/cards")
    List<MercadoPagoCardResponse> getCards(@PathVariable("customerId") String customerId);

    @PutMapping("/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse updateCard(
            @PathVariable("customerId") String customerId,
            @PathVariable("cardId") String cardId,
            @RequestBody MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest
    );


    // PAYMENTS

    @PostMapping("/payments")
    MercadoPagoPaymentResponse createPayment(@RequestBody MercadoPagoCreatePaymentRequest mercadoPagoCreatePaymentRequest,
                                             @RequestHeader("X-Idempotency-Key") UUID idempotencyId);

    @GetMapping("/payments/{paymentId}")
    MercadoPagoPaymentResponse getPayment(@PathVariable("paymentId") String paymentId);

    @PutMapping("/payments/{paymentId}")
    MercadoPagoPaymentResponse updatePayment(
            @PathVariable("paymentId") String paymentId,
            @RequestBody MercadoPagoUpdatePaymentRequest mercadoPagoUpdatePaymentRequest
    );

    @PostMapping("/payments/{paymentId}/refunds")
    MercadoPagoRefundResponse refundPayment(
            @PathVariable("paymentId") String paymentId,
            @RequestBody MercadoPagoRefundRequest mercadoPagoRefundRequest
    );

    @GetMapping("/payments/{paymentId}/refunds")
    List<MercadoPagoRefundResponse> getRefunds(@PathVariable("paymentId") String paymentId);

    @GetMapping("/payments/{paymentId}/refunds/{refundId}")
    MercadoPagoRefundResponse getRefund(
            @PathVariable("paymentId") String paymentId,
            @PathVariable("refundId") String refundId
    );


    // CARD TOKENS

    @PostMapping("/card_tokens")
    MercadoPagoCardTokenResponse generateCardToken(
            @RequestBody MercadoPagoCreateCardTokenRequest mercadoPagoCreateCardTokenRequest,
            @RequestParam("public_key") String publicKey
    );
}
