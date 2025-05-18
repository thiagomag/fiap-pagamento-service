package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCustomerAddressResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoCustomerResponse;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoIdentificationResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MercadoPagoCustomerAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var mercadoPagoCustomerResponse = MercadoPagoCustomerResponse.builder()
                .id("123")
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .identification(MercadoPagoIdentificationResponse.builder()
                        .type("type")
                        .number("number")
                        .build())
                .address(MercadoPagoCustomerAddressResponse.builder()
                        .streetName("streetName")
                        .streetNumber("123")
                        .zipCode("zipCode")
                        .id("addressId")
                        .build())
                .build();

        //when
        final var mercadoPagoCustomerAdapter = new MercadoPagoCustomerAdapter(jsonUtils).adapt(mercadoPagoCustomerResponse);

        //then
        assertNotNull(mercadoPagoCustomerAdapter);
        assertEquals(mercadoPagoCustomerResponse.getEmail(), mercadoPagoCustomerAdapter.getEmail());
        assertEquals(mercadoPagoCustomerResponse.getFirstName(), mercadoPagoCustomerAdapter.getFirstName());
        assertEquals(mercadoPagoCustomerResponse.getLastName(), mercadoPagoCustomerAdapter.getLastName());
        assertEquals(mercadoPagoCustomerResponse.getIdentification().getNumber(), mercadoPagoCustomerAdapter.getIdentificationNumber());
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        //given
        final var mercadoPagoCustomerResponse = MercadoPagoCustomerResponse.builder()
                .id("123")
                .email("email")
                .firstName("novo FirstName")
                .lastName("novo lastName")
                .identification(MercadoPagoIdentificationResponse.builder()
                        .type("type")
                        .number("number")
                        .build())
                .address(MercadoPagoCustomerAddressResponse.builder()
                        .streetName("streetName")
                        .streetNumber("123")
                        .zipCode("zipCode")
                        .id("addressId")
                        .build())
                .build();

        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        mercadoPagoCustomer.setClienteId(1L);
        mercadoPagoCustomer.setId(1L);
        mercadoPagoCustomer.setEmail(mercadoPagoCustomerResponse.getEmail());
        mercadoPagoCustomer.setFirstName("Nome Teste");
        mercadoPagoCustomer.setLastName("Sobrenome Teste");
        mercadoPagoCustomer.setMercadoPagoCustomerId("customerId");

        //when
        final var actual = new MercadoPagoCustomerAdapter(jsonUtils).adapt(mercadoPagoCustomerResponse, mercadoPagoCustomer);

        //then
        assertNotNull(actual);
        assertEquals("novo FirstName", actual.getFirstName());
        assertEquals("novo lastName", actual.getLastName());
    }
}
