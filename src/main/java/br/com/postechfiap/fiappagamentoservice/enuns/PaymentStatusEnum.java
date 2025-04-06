package br.com.postechfiap.fiappagamentoservice.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PaymentStatusEnum {

	PENDING("pending"),
	APPROVED("approved"),
	AUTHORIZED("authorized"),
	IN_PROCESS("in_process"),
	IN_MEDIATION("in_mediation"),
	REJECTED("rejected"),
	CANCELLED("cancelled"),
	REFUNDED("refunded"),
	CHARGED_BACK("charged_back");

	private final String status;

	public static PaymentStatusEnum findByStatus(final String status) {
		return Arrays.stream(values())
				.filter(value -> value.getStatus().equalsIgnoreCase(status))
				.findFirst()
				.orElse(null);
	}

}