package br.com.postechfiap.fiappagamentoservice.enuns;


import br.com.postechfiap.fiappagamentoservice.interfaces.EnumSerializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusBasicoEnum implements EnumSerializable {
    ATIVO("ATIVO"),
    INATIVO("INATIVO");

    private final String value;

}
