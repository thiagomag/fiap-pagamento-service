package br.com.postechfiap.fiappagamentoservice.controller.dto.response;

import br.com.postechfiap.fiappagamentoservice.enuns.MetodoPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PagamentoResponse {

    private Long id;
    private BigDecimal valor;
    private StatusPagamentoEnum status;
    private Long clienteId;
    private Long pedidoId;
    private Long mercadoPagoPaymentId;
    private Long perfilPagamentoId;
    private Integer parcelas;
    private MetodoPagamentoEnum metodoPagamento;
    private String codigoAutorizacao;
    private LocalDateTime authorizedAt;
    private LocalDateTime capturedAt;
}
