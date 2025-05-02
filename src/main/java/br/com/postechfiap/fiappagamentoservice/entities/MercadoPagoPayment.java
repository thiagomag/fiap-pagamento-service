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
@Table(name = Pagamento.TABLE_NAME)
@Entity
public class MercadoPagoPayment extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "mercado_pago_paymentid_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mercado_pago_customer_id", nullable = false)
    private MercadoPagoCustomer mercadoPagoCustomer;
    @ManyToOne
    @JoinColumn(name = "mercado_pago_card_id", nullable = false)
    private MercadoPagoCard mercadoPagoCard;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pagamento_id", nullable = false)
    private Pagamento pagamento;
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
        return 0L;
    }
}
