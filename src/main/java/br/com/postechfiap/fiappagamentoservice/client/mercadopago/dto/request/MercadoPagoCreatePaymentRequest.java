package br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoCreatePaymentRequest {

    private Boolean binaryMode;
    private Long campaignId;
    private Boolean capture;
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

    @Transient
    @JsonIgnore
    private String cardHolderName;

}