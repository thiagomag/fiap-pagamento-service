package br.com.postechfiap.fiappagamentoservice.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPagamentoEnum {
    PROCESSANDO("PROCESSANDO"),
    PENDENTE("PENDENTE"),
    SUCESSO("SUCESSO"),
    FALHA("FALHA"),
    CANCELADO("CANCELADO"),
    REEMBOLSADO("REEMBOLSADO");

    private final String descricao;

    public static StatusPagamentoEnum fromDescricao(String descricao) {
        for (StatusPagamentoEnum status : StatusPagamentoEnum.values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de pagamento inválido: " + descricao);
    }
}
