package br.com.postechfiap.fiappagamentoservice.client.dto.request;

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
@EqualsAndHashCode(callSuper=false)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoCreateCustomerRequest {

    private String email;
    private String firstName;
    private String lastName;
    private MercadoPagoIdentificationRequest identification;
    private MercadoPagoCustomerAddressRequest address;

}