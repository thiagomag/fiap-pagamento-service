package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.client.clienteService.dto.ClienteResponse;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.controller.dto.request.PerfilPagamentoRequest;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class MercadoPagoCreatePaymentRequestCustomAdapterTest {

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var pagamentoRequest = PagamentoRequest.builder()
                .idCliente(1L)
                .idPedido(UUID.randomUUID())
                .skuProduto("PRODTEST")
                .valor(BigDecimal.TEN)
                .perfilPagamento(PerfilPagamentoRequest.builder().build())
                .build();
        final var clienteResponse = ClienteResponse.builder()
                .id(1L)
                .nome("Nome Teste")
                .cpf("12345678901")
                .enderecos(null)
                .build();
        final var mercadoPagoCard = new MercadoPagoCard();
        mercadoPagoCard.setId(1L);
        mercadoPagoCard.setFirstSixDigits("123456");
        mercadoPagoCard.setLastFourDigits("7890");
        mercadoPagoCard.setExpirationMonth(11);
        mercadoPagoCard.setExpirationYear(25);
        mercadoPagoCard.setCardholderName("APRO");
        mercadoPagoCard.setToken("token");

        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        mercadoPagoCustomer.setClienteId(1L);
        mercadoPagoCustomer.setId(1L);
        mercadoPagoCustomer.setEmail("teste@teste.com");
        mercadoPagoCustomer.setFirstName("Nome");
        mercadoPagoCustomer.setLastName("Teste");
        mercadoPagoCustomer.setMercadoPagoCustomerId("customerId");

        final var pagamentoContext = PagamentoContext.builder()
                .pagamentoRequest(pagamentoRequest)
                .mercadoPagoCard(mercadoPagoCard)
                .clienteResponse(clienteResponse)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .build();

        //when
        final var actual = new MercadoPagoCreatePaymentRequestCustomAdapter().adapt(pagamentoContext);

        //then
        assertNotNull(actual);
        assertEquals(pagamentoRequest.getValor(), actual.getTransactionAmount());
        assertEquals("Nome", actual.getPayer().getFirstName());
        assertEquals("Teste", actual.getPayer().getLastName());
        assertEquals(clienteResponse.getCpf(), actual.getPayer().getIdentification().getNumber());
        assertEquals(mercadoPagoCard.getToken(), actual.getToken());
    }
}
