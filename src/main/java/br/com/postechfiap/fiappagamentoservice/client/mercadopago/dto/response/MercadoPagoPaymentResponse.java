package br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response;

import br.com.postechfiap.fiappagamentoservice.enuns.PaymentStatusDetailEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoPaymentResponse {

    private Long id;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateApproved;
    private OffsetDateTime dateLastUpdated;
    private String operationType;
    private String paymentMethodId;
    private String paymentTypeId;
    private String status;
    private String statusDetail;
    private String description;
    private String authorizationCode;
    private BigDecimal taxesAmount;
    private BigDecimal shippingAmount;
    private MercadoPagoPayerResponse payer;
    private Map<String, Object> metadata;
    private MercadoPagoPaymentAdditionalInfoResponse additionalInfo;
    private String externalReference;
    private BigDecimal transactionAmount;
    private BigDecimal transactionAmountRefunded;
    private Integer installments;
    private MercadoPagoPaymentTransactionDetailsResponse transactionDetails;
    private List<MercadoPagoPaymentFeeDetailsResponse> feeDetails;
    private Boolean captured;
    private Boolean binaryMode;
    private String statementDescriptor;
    private MercadoPagoCardResponse card;
    private String notificationUrl;
    private List<MercadoPagoRefundResponse> refunds;
    private String processingMode;

    @JsonIgnore
    public PaymentStatusEnum getStatusEnum() {
        return PaymentStatusEnum.findByStatus(status);
    }

    @JsonIgnore
    public PaymentStatusDetailEnum getStatusDetailEnum() {
        return PaymentStatusDetailEnum.findByStatusDetail(statusDetail);
    }

}