package br.com.postechfiap.fiappagamentoservice.client.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoPaymentAdditionalInfoRequest {

    private List<MercadoPagoAdditionalInfoItemRequest> items;
    private MercadoPagoAdditionalInfoPayerRequest payer;
    private String availableBalance;
    private String nsuProcessadora;
    private String authenticationCode;

}