package br.com.postechfiap.fiappagamentoservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = MercadoPagoCard.TABLE_NAME)
@Entity
@NoArgsConstructor
public class MercadoPagoCard extends BaseEntity<Long> {

    public static final String TABLE_NAME = "mercado_pago_card";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "mercado_pago_card_id_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mercado_pago_customer_id", nullable = false)
    private MercadoPagoCustomer mercadoPagoCustomer;
    private Integer expirationMonth;
    private Integer expirationYear;
    private String firstSixDigits;
    private String lastFourDigits;
    private String cardholderName;
    private String mercadoPagoPaymentMethod;

}
