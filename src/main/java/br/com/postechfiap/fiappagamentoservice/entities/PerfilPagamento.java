package br.com.postechfiap.fiappagamentoservice.entities;

import br.com.postechfiap.fiappagamentoservice.enuns.StatusBasicoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@Table(name = Pagamento.TABLE_NAME)
@Entity
public class PerfilPagamento extends BaseEntity<Long> {

    public static final String TABLE_NAME = "perfil_pagamento";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "pagamento_id_seq", allocationSize = 1)
    private Long id;
    private Long clienteId;
    private StatusBasicoEnum status;
    private String bandeiraCartao;
    private String primeirosNumerosCartao;
    private String ultimosNumerosCartao;
    private String nomeTitularCartao;
    private LocalDate dataValidade;
    private String tokenCartao;
    private Boolean cartaoPrincipal;
}
