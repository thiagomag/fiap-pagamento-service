package br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoCreateCardTokenRequest {

    private String cardNumber;
    private MercadoPagoCardHolderRequest cardHolder;
    private Integer expirationMonth;
    private Integer expirationYear;
    private String securityCode;
}
