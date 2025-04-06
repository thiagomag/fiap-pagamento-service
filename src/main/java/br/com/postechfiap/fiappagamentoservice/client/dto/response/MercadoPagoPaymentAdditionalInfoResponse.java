package br.com.postechfiap.fiappagamentoservice.client.dto.response;

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
public class MercadoPagoPaymentAdditionalInfoResponse {

    private List<MercadoPagoAdditionalInfoItemResponse> items;
    private MercadoPagoAdditionalInfoPayerResponse payer;
    private String availableBalance;
    private String nsuProcessadora;
    private String authenticationCode;

}