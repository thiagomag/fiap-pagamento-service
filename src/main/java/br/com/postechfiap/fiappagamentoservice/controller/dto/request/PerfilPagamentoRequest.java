package br.com.postechfiap.fiappagamentoservice.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Representa o perfil de pagamento")
public class PerfilPagamentoRequest {

    @Schema(description = "Número do cartão", example = "1234567890123456")
    private String numeroCartao;
    @Schema(description = "Código de segurança do cartão", example = "123")
    private String codigoSegurancaCartao;
    @Schema(description = "Nome do titular do cartão", example = "João da Silva")
    private String nomeTitularCartao;
    @Schema(description = "Data de validade do cartão", example = "2023-12-31")
    private LocalDate dataValidade;
}
