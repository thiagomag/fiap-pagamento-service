package br.com.postechfiap.fiappagamentoservice.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PaymentTypeEnum {

	ACCOUNT_MONEY("account_money"),
	TICKET("ticket"),
	BANK_TRANSFER("bank_transfer"),
	ATM("atm"),
	CREDIT_CARD("credit_card"),
	DEBIT_CARD("debit_card"),
	PREPAID_CARD("prepaid_card");

	private final String type;

	public static PaymentTypeEnum findByType(final String type) {
		return Arrays.stream(values())
				.filter(value -> value.getType().equalsIgnoreCase(type))
				.findFirst()
				.orElse(null);
	}

}