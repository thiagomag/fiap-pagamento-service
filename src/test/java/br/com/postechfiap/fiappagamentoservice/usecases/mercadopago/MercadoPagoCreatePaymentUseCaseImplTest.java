package br.com.postechfiap.fiappagamentoservice.usecases.mercadopago;

import br.com.postechfiap.fiappagamentoservice.adapter.MercadoPagoCreatePaymentRequestCustomAdapter;
import br.com.postechfiap.fiappagamentoservice.adapter.MercadoPagoPaymentCustomAdapter;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.MercadoPagoClient;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request.MercadoPagoCreatePaymentRequest;
import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.*;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoPayment;
import br.com.postechfiap.fiappagamentoservice.interfaces.repository.MercadoPagoPaymentRepository;
import br.com.postechfiap.fiappagamentoservice.interfaces.usecases.MercadoPagoCreatePaymentUseCase;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.MercadoPagoCreatePaymentUseCaseImpl;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MercadoPagoCreatePaymentUseCaseImplTest {

    @Mock
    private MercadoPagoCreatePaymentRequestCustomAdapter mercadoPagoCreatePaymentRequestCustomAdapter;
    @Mock
    private MercadoPagoPaymentCustomAdapter mercadoPagoPaymentAdapter;
    @Mock
    private MercadoPagoPaymentRepository mercadoPagoPaymentRepository;
    @Mock
    private MercadoPagoClient mercadoPagoClient;

    private MercadoPagoCreatePaymentUseCase mercadoPagoCreatePaymentUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mercadoPagoCreatePaymentUseCase = new MercadoPagoCreatePaymentUseCaseImpl(mercadoPagoCreatePaymentRequestCustomAdapter,
                mercadoPagoPaymentAdapter, mercadoPagoPaymentRepository, mercadoPagoClient);
    }

    @Test
    public void criarMercadoPagoPaymentDeveRetornarSucesso() {
        //giveb
        final var pagamentoContext = new PagamentoContext();
        final var mercadoPagoPaymentRequest = MercadoPagoCreatePaymentRequest.builder()
                .transactionAmount(BigDecimal.TEN)
                .token("test-token")
                .payer(null)
                .externalReference("test-reference")
                .installments(1)
                .binaryMode(true)
                .metadata(null)
                .cardHolderName("Test Holder")
                .build();
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
        final var mercadoPagoPayment = MercadoPagoPayment.builder()
                .id(mercadoPagoPaymentResponse.getId())
                .status(mercadoPagoPaymentResponse.getStatus())
                .statusDetail(mercadoPagoPaymentResponse.getStatusDetail())
                .paymentMethodId(mercadoPagoPaymentResponse.getPaymentMethodId())
                .paymentTypeId(mercadoPagoPaymentResponse.getPaymentTypeId())
                .operationType(mercadoPagoPaymentResponse.getOperationType())
                .build();

        when(mercadoPagoCreatePaymentRequestCustomAdapter.adapt(pagamentoContext))
                .thenReturn(mercadoPagoPaymentRequest);
        when(mercadoPagoClient.createPayment(any(MercadoPagoCreatePaymentRequest.class), any(UUID.class)))
                .thenReturn(mercadoPagoPaymentResponse);
        when(mercadoPagoPaymentAdapter.adapt(any(MercadoPagoPaymentResponse.class), any(PagamentoContext.class)))
                .thenReturn(mercadoPagoPayment);
        when(mercadoPagoPaymentRepository.save(mercadoPagoPayment))
                .thenAnswer(invocationOnMock -> {
                    final var savedPayment = invocationOnMock.getArgument(0, MercadoPagoPayment.class);
                    assertEquals(mercadoPagoPaymentResponse.getStatus(), savedPayment.getStatus());
                    return savedPayment;
                });

        //when
        final var actual = mercadoPagoCreatePaymentUseCase.execute(pagamentoContext);

        //then
        assertNotNull(actual);
        assertNotNull(actual.getMercadoPagoPayment());
    }
}
