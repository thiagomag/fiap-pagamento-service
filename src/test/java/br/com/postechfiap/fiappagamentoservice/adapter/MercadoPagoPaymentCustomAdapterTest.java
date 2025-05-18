package br.com.postechfiap.fiappagamentoservice.adapter;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoPaymentResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCard;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoCustomer;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class MercadoPagoPaymentCustomAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    private MercadoPagoPaymentCustomAdapter mercadoPagoPaymentCustomAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mercadoPagoPaymentCustomAdapter = new MercadoPagoPaymentCustomAdapter(jsonUtils);
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var mercadoPagoPaymentResponse = MercadoPagoPaymentResponse.builder()
                .id(23711752715L)
                .dateCreated(OffsetDateTime.now())
                .dateApproved(OffsetDateTime.now())
                .dateLastUpdated(OffsetDateTime.now())
                .operationType("recurring_payment")
                .paymentMethodId("master")
                .paymentTypeId("credit_card")
                .status("approved")
                .statusDetail("accredited")
                .authorizationCode("301299")
                .taxesAmount(BigDecimal.ZERO)
                .shippingAmount(BigDecimal.ZERO)
                .externalReference("test-reference")
                .transactionAmount(new BigDecimal("1000.5"))
                .transactionAmountRefunded(BigDecimal.ZERO)
                .installments(1)
                .captured(true)
                .binaryMode(false)
                .statementDescriptor("Mercadopago*fake")
                .build();
        final var mercadoPagoCard = new MercadoPagoCard();
        mercadoPagoCard.setId(12L);
        mercadoPagoCard.setFirstSixDigits("123456");
        mercadoPagoCard.setLastFourDigits("1234");
        mercadoPagoCard.setExpirationMonth(11);
        mercadoPagoCard.setExpirationYear(25);
        mercadoPagoCard.setCardholderName("APRO");

        final var mercadoPagoCustomer = new MercadoPagoCustomer();
        mercadoPagoCustomer.setClienteId(1L);
        mercadoPagoCustomer.setId(1L);
        mercadoPagoCustomer.setEmail("teste@teste.com");
        mercadoPagoCustomer.setFirstName("Nome Teste");
        mercadoPagoCustomer.setLastName("Sobrenome Teste");
        mercadoPagoCustomer.setMercadoPagoCustomerId("customerId");

        final var pagamentoContext = PagamentoContext.builder()
                .mercadoPagoCard(mercadoPagoCard)
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .build();

        //when
        final var actual = mercadoPagoPaymentCustomAdapter.adapt(mercadoPagoPaymentResponse, pagamentoContext);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getMercadoPagoPaymentId()).isEqualTo(mercadoPagoPaymentResponse.getId());
        assertThat(actual.getStatus()).isEqualTo(mercadoPagoPaymentResponse.getStatus());
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        assertThatThrownBy(() -> mercadoPagoPaymentCustomAdapter.adapt(new MercadoPagoPaymentResponse()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
