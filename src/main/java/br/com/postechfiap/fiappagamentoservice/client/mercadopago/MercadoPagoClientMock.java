package br.com.postechfiap.fiappagamentoservice.client.mercadopago;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.*;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.*;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MercadoPagoClientMock implements MercadoPagoClient {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int RANDOM_STRING_LENGTH = 14; // número de caracteres da parte aleatória
    private static final SecureRandom random = new SecureRandom();

    @Override
    public MercadoPagoCustomerResponse createCustomer(MercadoPagoCreateCustomerRequest req) {
        return MercadoPagoCustomerResponse.builder()
                .id(generateRandomId())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .identification(MercadoPagoIdentificationResponse.builder()
                        .type("CPF")
                        .number(req.getIdentification().getNumber())
                        .build())
                .address(MercadoPagoCustomerAddressResponse.builder()
                        .id(generateRandomId())
                        .streetNumber(req.getAddress().getStreetNumber().toString())
                        .streetName(req.getAddress().getStreetName())
                        .zipCode(req.getAddress().getZipCode())
                        .build())
                .build();
    }

    @Override
    public MercadoPagoCustomerResponse updateCustomer(String customerId, MercadoPagoUpdateCustomerRequest req) {
        return MercadoPagoCustomerResponse.builder()
                .id(customerId)
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .identification(MercadoPagoIdentificationResponse.builder()
                        .type("CPF")
                        .number(req.getIdentification().getNumber())
                        .build())
                .address(MercadoPagoCustomerAddressResponse.builder()
                        .id(generateRandomId())
                        .streetNumber(req.getAddress().getStreetNumber().toString())
                        .streetName(req.getAddress().getStreetName())
                        .zipCode(req.getAddress().getZipCode())
                        .build())
                .build();
    }

    @Override
    public MercadoPagoCardResponse createCard(String customerId, MercadoPagoCreateCardRequest req) {
        return MercadoPagoCardResponse.builder()
                .id(generateRandomLong(10))
                .firstSixDigits("503143")
                .lastFourDigits("6351")
                .expirationMonth(11)
                .expirationYear(2030)
                .cardholder(MercadoPagoCardHolderResponse.builder()
                        .identification(MercadoPagoIdentificationResponse.builder()
                                .type("CPF")
                                .number("12345678900")
                                .build())
                        .name(req.getCardHolderName())
                        .build())
                .paymentMethod(MercadoPagoPaymentMethodResponse.builder()
                        .id("master")
                        .paymentTypeId("credit_card")
                        .name("Mastercard")
                        .build())
                .customerId(customerId)
                .build();
    }

    @Override
    public MercadoPagoCardResponse deleteCard(String customerId, String cardId) {
        return createCard(customerId, MercadoPagoCreateCardRequest.builder()
                .cardHolderName("APRO")
                .build());
    }

    @Override
    public MercadoPagoPaymentResponse createPayment(MercadoPagoCreatePaymentRequest req, UUID idempotencyId) {
        String status;
        String statusDetail;
        OffsetDateTime dateApproved;

        if (req.getCardHolderName().equals("APRO")) {
            status = "approved";
            statusDetail = "accredited";
            dateApproved = OffsetDateTime.now();
        } else {
            status = "rejected";
            statusDetail = "cc_rejected_bad_filled_security_code";
            dateApproved = null;
        }

        return MercadoPagoPaymentResponse.builder()
                .id(23711752715L)
                .dateCreated(OffsetDateTime.now())
                .dateApproved(dateApproved)
                .dateLastUpdated(OffsetDateTime.now())
                .operationType("recurring_payment")
                .paymentMethodId("master")
                .paymentTypeId("credit_card")
                .status(status)
                .statusDetail(statusDetail)
                .description(req.getDescription())
                .authorizationCode("301299")
                .taxesAmount(BigDecimal.ZERO)
                .shippingAmount(BigDecimal.ZERO)
                .payer(MercadoPagoPayerResponse.builder()
                        .id(req.getPayer().getId())
                        .email(req.getPayer().getEmail())
                        .identification(MercadoPagoIdentificationResponse.builder()
                                .type(req.getPayer().getIdentification().getType())
                                .number(req.getPayer().getIdentification().getNumber())
                                .build())
                        .build())
                .metadata(req.getMetadata())
                .externalReference(req.getExternalReference())
                .transactionAmount(new BigDecimal("1000.5"))
                .transactionAmountRefunded(BigDecimal.ZERO)
                .installments(1)
                .captured(true)
                .binaryMode(false)
                .statementDescriptor("Mercadopago*fake")
                .card(MercadoPagoCardResponse.builder()
                        .id(9127951339L)
                        .firstSixDigits("503143")
                        .lastFourDigits("6351")
                        .expirationMonth(11)
                        .expirationYear(2025)
                        .cardholder(MercadoPagoCardHolderResponse.builder()
                                .name(req.getCardHolderName())
                                .identification(MercadoPagoIdentificationResponse.builder()
                                        .type("CPF")
                                        .number("1234567890")
                                        .build())
                                .build())
                        .build())
                .build();
    }

    @Override
    public MercadoPagoCardTokenResponse generateCardToken(MercadoPagoCreateCardTokenRequest req, String publicKey) {
        return MercadoPagoCardTokenResponse.builder()
                .id(generateCardToken())
                .build();
    }

    public static long generateRandomLong(int length) {
        long min = (long) Math.pow(10, length - 1);  // ex: 1000000000
        long max = (long) Math.pow(10, length) - 1;  // ex: 9999999999
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    public static String generateRandomId() {
        long timestamp = System.currentTimeMillis() / 1000; // timestamp em segundos
        StringBuilder randomPart = new StringBuilder(RANDOM_STRING_LENGTH);

        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            randomPart.append(CHARACTERS.charAt(index));
        }

        return timestamp + "-" + randomPart;
    }

    public static String generateCardToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
