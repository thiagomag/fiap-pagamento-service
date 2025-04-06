package br.com.postechfiap.fiappagamentoservice.client.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoCardResponse {

    private Long id;
    private MercadoPagoCardHolderResponse cardholder;
    private String customerId;
    private Integer expirationMonth;
    private Integer expirationYear;
    private String firstSixDigits;
    private String lastFourDigits;
    private MercadoPagoPaymentMethodResponse paymentMethod;

}