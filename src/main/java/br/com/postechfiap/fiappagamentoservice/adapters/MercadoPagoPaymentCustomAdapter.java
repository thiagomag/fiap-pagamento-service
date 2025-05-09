package br.com.postechfiap.fiappagamentoservice.adapters;

import br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response.MercadoPagoPaymentResponse;
import br.com.postechfiap.fiappagamentoservice.entities.MercadoPagoPayment;
import br.com.postechfiap.fiappagamentoservice.interfaces.CustomAdapter;
import br.com.postechfiap.fiappagamentoservice.usecase.mercadoPago.dto.PagamentoContext;
import br.com.postechfiap.fiappagamentoservice.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MercadoPagoPaymentCustomAdapter implements CustomAdapter<MercadoPagoPaymentResponse, MercadoPagoPayment> {

    private final JsonUtils jsonUtils;

    @Override
    public MercadoPagoPayment adapt(MercadoPagoPaymentResponse mercadoPagoPaymentResponse) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MercadoPagoPayment adapt(MercadoPagoPaymentResponse mercadoPagoPaymentResponse, Object... args) {
        Assert.isTrue(args[0] instanceof PagamentoContext, "MercadoPagoPaymentCustomAdapter.adapt.args[1][0] precisa ser uma instância de PagamentoContext!");

        final var pagamentoContext = (PagamentoContext) args[0];
        final var mercadoPagoCard = pagamentoContext.getMercadoPagoCard();
        final var mercadoPagoCustomer = pagamentoContext.getMercadoPagoCustomer();

        return MercadoPagoPayment.builder()
                .mercadoPagoCustomer(mercadoPagoCustomer)
                .mercadoPagoPaymentId(mercadoPagoPaymentResponse.getId())
                .status(mercadoPagoPaymentResponse.getStatus())
                .paymentMethodId(mercadoPagoPaymentResponse.getPaymentMethodId())
                .transactionAmount(mercadoPagoPaymentResponse.getTransactionAmount())
                .installments(mercadoPagoPaymentResponse.getInstallments())
                .paymentTypeId(mercadoPagoPaymentResponse.getPaymentTypeId())
                .mercadoPagoCard(mercadoPagoCard)
                .authorizationCode(mercadoPagoPaymentResponse.getAuthorizationCode())
                .operationType(mercadoPagoPaymentResponse.getOperationType())
                .statusDetail(mercadoPagoPaymentResponse.getStatusDetail())
                .response(jsonUtils.writeValueAsStringOrNull(mercadoPagoPaymentResponse))
                .authorizedAt(Optional.ofNullable(mercadoPagoPaymentResponse.getDateApproved()).map(OffsetDateTime::toLocalDateTime).orElse(null))
                .capturedAt(mercadoPagoPaymentResponse.getDateCreated().toLocalDateTime())
                .build();
    }
}
