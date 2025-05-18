package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardHolderResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCardResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoIdentificationResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MercadoPagoCardAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var mercadoPagoCardResponse = MercadoPagoCardResponse.builder()
                .id(1L)
                .firstSixDigits("123456")
                .lastFourDigits("3456")
                .expirationMonth(12)
                .expirationYear(30)
                .cardholder(MercadoPagoCardHolderResponse.builder()
                        .identification(MercadoPagoIdentificationResponse.builder()
                                .type("CPF")
                                .number("12345678900")
                                .build())
                        .name("John Doe")
                        .build())
                .build();

        //when
        final var actual = new MercadoPagoCardAdapter(jsonUtils).adapt(mercadoPagoCardResponse);

        //then
        assertEquals(mercadoPagoCardResponse.getExpirationMonth(), actual.getExpirationMonth());
        assertEquals(mercadoPagoCardResponse.getExpirationYear(), actual.getExpirationYear());
        assertEquals(mercadoPagoCardResponse.getFirstSixDigits(), actual.getFirstSixDigits());
        assertEquals(mercadoPagoCardResponse.getLastFourDigits(), actual.getLastFourDigits());
        assertEquals(mercadoPagoCardResponse.getCardholder().getName(), actual.getCardholderName());
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        //given
        final var mercadoPagoCardResponse = MercadoPagoCardResponse.builder()
                .id(1L)
                .firstSixDigits("123456")
                .lastFourDigits("3456")
                .expirationMonth(12)
                .expirationYear(30)
                .cardholder(MercadoPagoCardHolderResponse.builder()
                        .identification(MercadoPagoIdentificationResponse.builder()
                                .type("CPF")
                                .number("12345678900")
                                .build())
                        .name("John Doe")
                        .build())
                .build();

        final var mercadoPagoCard = new MercadoPagoCard();
        mercadoPagoCard.setId(mercadoPagoCardResponse.getId());
        mercadoPagoCard.setFirstSixDigits(mercadoPagoCardResponse.getFirstSixDigits());
        mercadoPagoCard.setLastFourDigits(mercadoPagoCardResponse.getLastFourDigits());
        mercadoPagoCard.setExpirationMonth(11);
        mercadoPagoCard.setExpirationYear(25);
        mercadoPagoCard.setCardholderName(mercadoPagoCardResponse.getCardholder().getName());

        //when
        final var actual = new MercadoPagoCardAdapter(jsonUtils).adapt(mercadoPagoCardResponse, mercadoPagoCard);

        //then
        assertEquals(mercadoPagoCardResponse.getExpirationMonth(), actual.getExpirationMonth());
        assertEquals(mercadoPagoCardResponse.getExpirationYear(), actual.getExpirationYear());
        assertEquals(mercadoPagoCardResponse.getFirstSixDigits(), actual.getFirstSixDigits());
        assertEquals(mercadoPagoCardResponse.getLastFourDigits(), actual.getLastFourDigits());
        assertEquals(mercadoPagoCardResponse.getCardholder().getName(), actual.getCardholderName());
    }
}
