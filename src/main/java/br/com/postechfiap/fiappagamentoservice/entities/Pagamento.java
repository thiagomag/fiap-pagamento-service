package br.com.postechfiap.fiappagamentoservice.entities;

import br.com.postechfiap.fiappagamentoservice.enuns.MetodoPagamentoEnum;
import br.com.postechfiap.fiappagamentoservice.enuns.StatusPagamentoEnum;
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
public class Pagamento extends BaseEntity<Long> {

    public static final String TABLE_NAME = "pagamento";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "pagamento_id_seq", allocationSize = 1)
    private Long id;
    private BigDecimal valor;
    private StatusPagamentoEnum status;
    private Long clienteId;
    private Long pedidoId;
    @OneToOne(mappedBy = "pagamento")
    private MercadoPagoPayment mercadoPagoPayment;
    @ManyToOne
    @JoinColumn(name = "perfil_pagamento_id")
    private PerfilPagamento perfilPagamento;
    private Integer parcelas;
    private MetodoPagamentoEnum metodoPagamento;
    private String codigoAutorizacao;
    private LocalDateTime authorizedAt;
    private LocalDateTime capturedAt;

}
