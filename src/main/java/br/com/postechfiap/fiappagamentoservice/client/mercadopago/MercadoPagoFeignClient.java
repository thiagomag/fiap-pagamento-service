package br.com.postechfiap.fiappagamentoservice.client.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardTokenResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCustomerResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoPaymentResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "MercadoPagoClient", url = "${client.mercadopago.url}")
@ConditionalOnProperty(name = "mock.mercado-pago.enabled", havingValue = "false", matchIfMissing = true)
public interface MercadoPagoFeignClient extends MercadoPagoClient {

    // CUSTOMERS
    @PostMapping("/customers")
    MercadoPagoCustomerResponse createCustomer(@RequestBody MercadoPagoCreateCustomerRequest mercadoPagoCreateCustomerRequest);

    @PutMapping("/customers/{customerId}")
    MercadoPagoCustomerResponse updateCustomer(
            @PathVariable("customerId") String customerId,
            @RequestBody MercadoPagoUpdateCustomerRequest mercadoPagoUpdateCustomerRequest
    );

    // CARDS
    @PostMapping("/customers/{customerId}/cards")
    MercadoPagoCardResponse createCard(
            @PathVariable("customerId") String customerId,
            @RequestBody MercadoPagoCreateCardRequest mercadoPagoCreateCardRequest
    );

    @DeleteMapping("/customers/{customerId}/cards/{cardId}")
    MercadoPagoCardResponse deleteCard(
            @PathVariable("customerId") String customerId,
            @PathVariable("cardId") String cardId
    );

    // PAYMENTS
    @PostMapping("/payments")
    MercadoPagoPaymentResponse createPayment(@RequestBody MercadoPagoCreatePaymentRequest mercadoPagoCreatePaymentRequest,
                                             @RequestHeader("X-Idempotency-Key") UUID idempotencyId);

    // CARD TOKENS
    @PostMapping("/card_tokens")
    MercadoPagoCardTokenResponse generateCardToken(
            @RequestBody MercadoPagoCreateCardTokenRequest mercadoPagoCreateCardTokenRequest,
            @RequestParam("public_key") String publicKey
    );
}
