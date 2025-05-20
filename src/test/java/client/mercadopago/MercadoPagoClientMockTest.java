package client.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClientMock;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MercadoPagoClientMockTest {

    private final MercadoPagoClientMock client = new MercadoPagoClientMock();

    @Test
    @DisplayName("Deve criar customer com dados corretos")
    void testCreateCustomer() {
        var req = MercadoPagoCreateCustomerRequest.builder()
                .firstName("Fulano")
                .lastName("Silva")
                .email("fulano@email.com")
                .identification(MercadoPagoIdentificationRequest.builder().number("12345678900").build())
                .address(MercadoPagoCustomerAddressRequest.builder()
                        .streetName("Rua A")
                        .streetNumber(123)
                        .zipCode("12345-678")
                        .build())
                .build();

        var response = client.createCustomer(req);

        assertEquals("Fulano", response.getFirstName());
        assertEquals("Silva", response.getLastName());
        assertEquals("12345678900", response.getIdentification().getNumber());
        assertNotNull(response.getId());
        assertNotNull(response.getAddress().getId());
    }

    @Test
    @DisplayName("Deve atualizar customer com ID correto")
    void testUpdateCustomer() {
        var req = MercadoPagoUpdateCustomerRequest.builder()
                .firstName("Ciclano")
                .lastName("Souza")
                .identification(MercadoPagoIdentificationRequest.builder().number("99999999999").build())
                .address(MercadoPagoCustomerAddressRequest.builder()
                        .streetName("Av B")
                        .streetNumber(456)
                        .zipCode("98765-432")
                        .build())
                .build();

        var response = client.updateCustomer("abc123", req);

        assertEquals("abc123", response.getId());
        assertEquals("Ciclano", response.getFirstName());
        assertEquals("Souza", response.getLastName());
    }

    @Test
    @DisplayName("Deve criar cartão com dados fixos e customerId")
    void testCreateCard() {
        var req = MercadoPagoCreateCardRequest.builder()
                .cardHolderName("Fulano Card")
                .build();

        var response = client.createCard("cust123", req);

        assertEquals("cust123", response.getCustomerId());
        assertEquals("503143", response.getFirstSixDigits());
        assertEquals("6351", response.getLastFourDigits());
    }

    @Test
    @DisplayName("Deve deletar cartão simulando criação de novo")
    void testDeleteCard() {
        var response = client.deleteCard("custX", "cardY");

        assertEquals("custX", response.getCustomerId());
        assertEquals("APRO", response.getCardholder().getName());
    }

    @Test
    @DisplayName("Deve criar pagamento aprovado se titular for APRO")
    void testCreatePaymentApproved() {
        var req = MercadoPagoCreatePaymentRequest.builder()
                .cardHolderName("APRO")
                .description("Compra teste")
                .payer(MercadoPagoPayerRequest.builder()
                        .id("payer123")
                        .email("payer@email.com")
                        .identification(MercadoPagoIdentificationRequest.builder()
                                .type("CPF")
                                .number("12345678900")
                                .build())
                        .build())
                .metadata(Map.of())
                .externalReference("ref123")
                .build();

        var response = client.createPayment(req, UUID.randomUUID());

        assertEquals("approved", response.getStatus());
        assertNotNull(response.getDateApproved());
    }

    @Test
    @DisplayName("Deve criar pagamento rejeitado se titular for diferente de APRO")
    void testCreatePaymentRejected() {
        var req = MercadoPagoCreatePaymentRequest.builder()
                .cardHolderName("FULANO")
                .description("Compra teste")
                .payer(MercadoPagoPayerRequest.builder()
                        .id("payer456")
                        .email("fulano@email.com")
                        .identification(MercadoPagoIdentificationRequest.builder()
                                .type("CPF")
                                .number("11122233344")
                                .build())
                        .build())
                .metadata(Map.of())
                .externalReference("ref999")
                .build();

        var response = client.createPayment(req, UUID.randomUUID());

        assertEquals("rejected", response.getStatus());
        assertNull(response.getDateApproved());
    }

    @Test
    @DisplayName("Deve gerar token de cartão com 64 caracteres hex")
    void testGenerateCardToken() {
        var req = MercadoPagoCreateCardTokenRequest.builder().build();
        var tokenResponse = client.generateCardToken(req, "publicKey");

        assertNotNull(tokenResponse.getId());
        assertEquals(64, tokenResponse.getId().length());
    }

    @Test
    @DisplayName("generateRandomLong deve gerar valor dentro do range")
    void testGenerateRandomLong() {
        long value = MercadoPagoClientMock.generateRandomLong(10);
        assertTrue(value >= 1_000_000_000L && value <= 9_999_999_999L);
    }

    @Test
    @DisplayName("generateRandomId deve gerar string com timestamp + hífen + 14 caracteres")
    void testGenerateRandomId() {
        String id = MercadoPagoClientMock.generateRandomId();
        assertTrue(id.matches("\\d{10}-[A-Za-z0-9]{14}"));
    }

    @Test
    @DisplayName("generateCardToken deve gerar string hexadecimal de 64 caracteres")
    void testGenerateTokenFormat() {
        String token = MercadoPagoClientMock.generateCardToken();
        assertTrue(token.matches("[a-f0-9]{64}"));
    }
}
