package br.com.postechfiap.fiappagamentoservice.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PaymentStatusDetailEnum {

	ACCREDITED("accredited"),
	REFUNDED("refunded"),
	PARTIALLY_REFUNDED("partially_refunded"),
	PENDING_CONTINGENCY("pending_contingency"),
	PENDING_REVIEW_MANUAL("pending_review_manual"),
	PENDING_WAITING_PAYMENT("pending_waiting_payment"),
	PENDING_WAITING_TRANSFER("pending_waiting_transfer"),
	CC_REJECTED_BAD_FILLED_DATE("cc_rejected_bad_filled_date"),
	CC_REJECTED_BAD_FILLED_OTHER("cc_rejected_bad_filled_other"),
	CC_REJECTED_BAD_FILLED_SECURITY_CODE("cc_rejected_bad_filled_security_code"),
	CC_REJECTED_BLACKLIST("cc_rejected_blacklist"),
	CC_REJECTED_CALL_FOR_AUTHORIZE("cc_rejected_call_for_authorize"),
	CC_REJECTED_CARD_DISABLED("cc_rejected_card_disabled"),
	CC_REJECTED_DUPLICATED_PAYMENT("cc_rejected_duplicated_payment"),
	CC_REJECTED_HIGH_RISK("cc_rejected_high_risk"),
	CC_REJECTED_INSUFFICIENT_AMOUNT("cc_rejected_insufficient_amount"),
	CC_REJECTED_INVALID_INSTALLMENTS("cc_rejected_invalid_installments"),
	CC_REJECTED_MAX_ATTEMPTS("cc_rejected_max_attempts"),
	CC_REJECTED_OTHER_REASON("cc_rejected_other_reason"),
	REJECTED_HIGH_RISK("rejected_high_risk"),
	PENDING_CAPTURE("pending_capture"),
	BY_COLLECTOR("by_collector"),
	EXPIRED("expired");

	private final String statusDetail;

	public static PaymentStatusDetailEnum findByStatusDetail(final String statusDetail) {
		return Arrays.stream(values())
				.filter(value -> value.getStatusDetail().equalsIgnoreCase(statusDetail))
				.findFirst()
				.orElse(null);
	}

}