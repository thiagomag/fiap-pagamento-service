package br.com.postechfiap.fiappagamentoservice.entities;

import jakarta.persistence.*;
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
@Table(name = MercadoPagoPayment.TABLE_NAME)
@Entity
public class MercadoPagoPayment extends BaseEntity<Long> {

    public static final String TABLE_NAME = "mercado_pago_payment";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "mercado_pago_payment_id_seq", allocationSize = 1)
    private Long id;
    private Long mercadoPagoPaymentId;
    @ManyToOne
    @JoinColumn(name = "mercado_pago_customer_id", nullable = false)
    private MercadoPagoCustomer mercadoPagoCustomer;
    @ManyToOne
    @JoinColumn(name = "mercado_pago_card_id", nullable = false)
    private MercadoPagoCard mercadoPagoCard;
    private String operationType;
    private Integer installments;
    private BigDecimal transactionAmount;
    private BigDecimal transactionAmountRefunded;
    private LocalDateTime authorizedAt;
    private LocalDateTime capturedAt;
    private String paymentMethodId;
    private String paymentTypeId;
    private String status;
    private String statusDetail;
    private String authorizationCode;
    private String response;

    @Override
    public Long getId() {
        return this.id;
    }
}
