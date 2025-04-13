package br.com.postechfiap.fiappagamentoservice.client.mercadopago.dto.response;

import br.com.postechfiap.fiappagamentoservice.enuns.RefundStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoRefundResponse {

    private Long id;
    private Long paymentId;
    private BigDecimal amount;
    private LocalDateTime dateCreated;
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