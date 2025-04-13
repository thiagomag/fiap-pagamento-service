package br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoCreatePaymentRequest {

    private MercadoPagoPaymentAdditionalInfoRequest additionalInfo;
    private Boolean binaryMode;
    private Long campaignId;
    private Boolean capture;
    private BigDecimal couponAmount;
    private String couponCode;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime dateOfExpiration;
    private String description;
    private String externalReference;
    private Integer installments;
    private Map<String, Object> metadata;
    private String notificationUrl;
    private MercadoPagoPayerRequest payer;
    private String paymentMethodId;
    private String statementDescriptor;
    private String token;
    private BigDecimal transactionAmount;

}