package br.com.postechfiap.fiappagamentoservice.enuns;


import br.com.postechfiap.fiappagamentoservice.interfaces.EnumSerializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MetodoPagamentoEnum implements EnumSerializable {

    CARTAO_CREDITO("CARTAO_CREDITO"),
    DEBITO("CARTAO_DEBITO"),
    BOLETO("BOLETO"),
    PIX("PIX"),
    TRANSFERENCIA("TRANSFERENCIA");

    private final String metodoPagamento;

    @Override
    public String getValue() {
        return this.metodoPagamento.trim();
    }
}
