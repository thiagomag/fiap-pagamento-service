package br.com.postechfiap.fiappagamentoservice.client.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoAdditionalInfoItemRequest {

    private Long id;
    private String title;
    private String description;
    @Builder.Default
    private Integer quantity = 1;
    private BigDecimal unitPrice;

}