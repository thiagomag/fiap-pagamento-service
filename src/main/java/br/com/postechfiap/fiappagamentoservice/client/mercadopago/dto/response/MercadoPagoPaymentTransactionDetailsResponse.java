package br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoPaymentTransactionDetailsResponse {

    private String paymentMethodReferenceId;
    private BigDecimal netReceivedAmount;
    private BigDecimal totalPaidAmount;
    private BigDecimal overpaidAmount;
    private String externalResourceUrl;
    private BigDecimal installmentAmount;
    private String financialInstitution;
    private String payableDeferralPeriod;
    private String acquirerReference;
    private String verificationCode;

}