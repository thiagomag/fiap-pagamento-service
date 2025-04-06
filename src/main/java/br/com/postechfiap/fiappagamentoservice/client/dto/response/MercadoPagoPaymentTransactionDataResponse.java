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
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoPaymentTransactionDataResponse {

    private String qrCode;
    private String bankTransferId;
    private String transactionId;
    private String financialInstitution;
    private String ticketUrl;
    private MercadoPagoPaymentBankInfoResponse bankInfo;
    private String qrCodeBase64;

}