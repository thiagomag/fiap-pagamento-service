package br.com.postechfiap.fiappagamentoservice.client.dto.response;

import br.com.postechfiap.fiappagamentoservice.enuns.PaymentStatusDetailEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoPaymentResponse {

    private Long id;
    private LocalDateTime dateCreated;
    private LocalDateTime dateApproved;
    private LocalDateTime dateLastUpdated;
    private LocalDateTime dateOfExpiration;
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
    private BigDecimal couponAmount;
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
    private MercadoPagoPaymentBarcodeResponse barcode;
    private MercadoPagoPaymentPointOfInteractionResponse pointOfInteraction;

    @JsonIgnore
    public PaymentStatusEnum getStatusEnum() {
        return PaymentStatusEnum.findByStatus(status);
    }

    @JsonIgnore
    public PaymentStatusDetailEnum getStatusDetailEnum() {
        return PaymentStatusDetailEnum.findByStatusDetail(statusDetail);
    }

    @JsonIgnore
    public String getMercadoPagoCustomerId() {
        return Optional.ofNullable(getPayer())
                .map(MercadoPagoPayerResponse::getId)
                .orElse(null);
    }

    @JsonIgnore
    public String getPixCode() {
        return Optional.ofNullable(getPointOfInteraction())
                .filter(pointOfInteraction -> pointOfInteraction.getTransactionData() != null)
                .map(MercadoPagoPaymentPointOfInteractionResponse::getTransactionData)
                .map(MercadoPagoPaymentTransactionDataResponse::getQrCode)
                .orElse(null);
    }

    @JsonIgnore
    public String getTicketUrl() {
        return Optional.ofNullable(getPointOfInteraction())
                .filter(pointOfInteraction -> pointOfInteraction.getTransactionData() != null)
                .map(MercadoPagoPaymentPointOfInteractionResponse::getTransactionData)
                .map(MercadoPagoPaymentTransactionDataResponse::getTicketUrl)
                .orElse(null);
    }

    @JsonIgnore
    public String getQRCodeBase64() {
        return Optional.ofNullable(getPointOfInteraction())
                .filter(pointOfInteraction -> pointOfInteraction.getTransactionData() != null)
                .map(MercadoPagoPaymentPointOfInteractionResponse::getTransactionData)
                .map(MercadoPagoPaymentTransactionDataResponse::getQrCodeBase64)
                .orElse(null);
    }

    @JsonIgnore
    public String getBarcodeContent() {
        return Optional.ofNullable(getBarcode())
                .map(MercadoPagoPaymentBarcodeResponse::getContent)
                .orElse(null);
    }

    @JsonIgnore
    @Transient
    public boolean isPaymentStatusRejected() {
        return PaymentStatusEnum.REJECTED.equals(getStatusEnum());
    }

    @JsonIgnore
    @Transient
    public boolean isPaymentStatusDetailRefunded() {
        return PaymentStatusDetailEnum.REFUNDED.equals(getStatusDetailEnum());
    }

    @JsonIgnore
    @Transient
    public boolean isPaymentStatusDetailPartiallyRefunded() {
        return PaymentStatusDetailEnum.PARTIALLY_REFUNDED.equals(getStatusDetailEnum());
    }

    @JsonIgnore
    @Transient
    public boolean isPaymentStatusApproved() {
        return PaymentStatusEnum.APPROVED.equals(getStatusEnum());
    }

    @JsonIgnore
    @Transient
    public boolean isPaymentStatusPending() {
        return PaymentStatusEnum.PENDING.equals(getStatusEnum());
    }

    @JsonIgnore
    @Transient
    public boolean isBrokerGeneratedTransaction() {
        return Optional.ofNullable(metadata)
                .map(m -> m.get("rejected_payment_reference"))
                .isPresent();
    }

    @JsonIgnore
    @Transient
    public Long getRejectedPaymentReferenceId() {
        return Optional.ofNullable(metadata)
                .map(m -> m.get("rejected_payment_reference"))
                .map(Object::toString)
                .map(Long::valueOf)
                .orElse(0L);
    }

    @JsonIgnore
    @Transient
    public int getAvailableTries() {
        return Optional.ofNullable(metadata)
                .map(m -> m.get("available_tries"))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElse(0);
    }

    @JsonIgnore
    @Transient
    public boolean isRetryable() {
        return Optional.ofNullable(metadata)
                .map(m -> m.get("retryable"))
                .map(Object::toString)
                .map(Boolean::valueOf)
                .orElse(false);
    }

    @JsonIgnore
    @Transient
    public boolean isNotRetryable() {
        return !isRetryable();
    }

    @JsonIgnore
    @Transient
    public BigDecimal getTransactedAmount() {
        return transactionAmount;
    }

    @JsonIgnore
    @Transient
    public Integer getTransactedInstallment() {
        return installments;
    }

}