package br.com.postechfiap.fiappagamentoservice.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Representa o pedido de pagamento")
public class PagamentoRequest {

    @Schema(description = "Valor do pagamento", example = "100.00")
    private BigDecimal valor;
    @Schema(description = "ID do cliente", example = "123456789")
    private Long idCliente;
    @Schema(description = "ID do pedido", example = "123456789")
    private Long idPedido;
    @Schema(description = "ID do produto", example = "123456789")
    private UUID idProduto;
    @Schema(description = "Request para perfil de pagamneto")
    private PerfilPagamentoRequest perfilPagamento;
}
