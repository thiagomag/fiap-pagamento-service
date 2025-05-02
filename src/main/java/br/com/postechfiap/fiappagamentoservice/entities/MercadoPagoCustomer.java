package br.com.postechfiap.fiappagamentoservice.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = MercadoPagoCustomer.TABLE_NAME)
@Entity
@NoArgsConstructor
public class MercadoPagoCustomer extends BaseEntity<Long> {

    public static final String TABLE_NAME = "mercado_pago_customer";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "mercado_pago_customer_id_seq", allocationSize = 1)
    private Long id;
    private String mercadoPagoCustomerId;
    private Long clienteId;
    private String firstName;
    private String lastName;
    private String email;
    private String identificationNumber;
    private String response;

    @Override
    public Long getId() {
        return this.id;
    }
}