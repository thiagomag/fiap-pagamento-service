package br.com.postechfiap.fiappagamentoservice.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum IdentificationTypeEnum {

	CPF("CPF"),
	CNPJ("CNPJ");

	private final String type;

	public static IdentificationTypeEnum findByType(final String type) {
		return Arrays.stream(values())
				.filter(value -> value.getType().equalsIgnoreCase(type))
				.findFirst()
				.orElse(null);
	}

}