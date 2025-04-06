package br.com.postechfiap.fiappagamentoservice.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RefundStatusEnum {

	APPROVED("approved"),
	IN_PROCESS("in_process"),
	REJECTED("rejected"),
	CANCELLED("cancelled"),
	AUTHORIZED("authorized");

	private final String status;

	public static RefundStatusEnum findByStatus(final String status) {
		return Arrays.stream(values())
				.filter(value -> value.getStatus().equalsIgnoreCase(status))
				.findFirst()
				.orElse(null);
	}

}