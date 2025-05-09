package br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response;

import br.com.postechfiap.fiappagamentoservice.enuns.RefundStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoRefundResponse {

    private Long id;
    private Long paymentId;
    private BigDecimal amount;
    private OffsetDateTime dateCreated;
    private String refundMode;
    private BigDecimal adjustmentAmount;
    private String status;
    private String reason;
    private BigDecimal amountRefundedToPayer;
    private String uniqueSequenceNumber;

    @JsonIgnore
    public RefundStatusEnum getStatusEnum() {
        return RefundStatusEnum.findByStatus(status);
    }

}