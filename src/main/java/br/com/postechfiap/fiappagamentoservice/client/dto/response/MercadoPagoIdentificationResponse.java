package br.com.postechfiap.fiappagamentoservice.client.dto.response;

import br.com.postechfiap.fiappagamentoservice.enuns.IdentificationTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class MercadoPagoIdentificationResponse {

    private String type;
    private String number;

    @JsonIgnore
    public IdentificationTypeEnum getTypeEnum() {
        return IdentificationTypeEnum.findByType(type);
    }

}