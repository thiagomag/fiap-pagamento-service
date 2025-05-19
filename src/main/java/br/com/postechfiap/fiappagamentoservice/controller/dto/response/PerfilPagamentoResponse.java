package br.com.postechfiap.fiappagamentoservice.controller.dto.response;

import br.com.postechfiap.fiappagamentoservice.enuns.StatusBasicoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PerfilPagamentoResponse {

    private Long id;
    private Long clienteId;
    private StatusBasicoEnum status;
    private String primeirosNumerosCartao;
    private String ultimosNumerosCartao;
    private String nomeTitularCartao;
    private LocalDate dataValidade;
}
