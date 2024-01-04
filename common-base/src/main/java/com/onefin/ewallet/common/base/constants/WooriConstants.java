package com.onefin.ewallet.common.base.constants;

public class WooriConstants extends OneFinConstants {

	// Response Code
	public static final String PROCESS_COMPLETED = "0000";

	public static final String DIFFERENT_CURRENCY = "0001";

	public static final String OMITTED_REQUIRED_FIELD = "0002";

	public static final String NOT_VALID_INPUT = "0003";

	public static final String UNAVAILABLE_CURRENCY = "0007";

	public static final String UNKNOWN_ERROR_CODE = "U/N";


	// domain for bvb
	public static final String CORE_SYSTEM = "CORE_SYSTEM";
	public static final String NAPAS = "NAPAS";
	public static final String FIRM_BANKING_SYSTEM = "FIRM_BANKING_SYSTEM";
	public static final String UNKNOWN = "UNKNOWN_ERROR_CODE";

	/******************** Backup type **********************/

	public static final String BACKUP_HEADER = "HEADER";

	public static final String WOORI_BANK_CODE = "WOORI";

	public enum WooriVirtualAcctErrorCode {
		INVALID_PARAM_REQUEST(PARTNER_WOORI, CORE_SYSTEM, NOT_VALID_INPUT),
		UNKNOWN_ERROR(PARTNER_WOORI, CORE_SYSTEM, UNKNOWN_ERROR_CODE),
		QUERY_SUCCESS(PARTNER_WOORI, CORE_SYSTEM, PROCESS_COMPLETED);

		private final String partnerCode;

		private final String domainCode;

		private final String code;


		WooriVirtualAcctErrorCode(String partnerCode, String domainCode, String code) {
			this.partnerCode = partnerCode;
			this.domainCode = domainCode;
			this.code = code;
		}

		public String getPartnerCode() {
			return partnerCode;
		}

		public String getDomainCode() {
			return domainCode;
		}

		public String getCode() {
			return code;
		}
	}

	public enum WooriVirtualAcctIssueCode {
		REALTIME("001"),
		CORE_BANK_RANDOM("901");

		private final String value;

		WooriVirtualAcctIssueCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum WooriVirtualAcctStatus {
		REGIST("10", "REGIST"),
		UNKNOWN("UNKNOWN", "UNKNOWN");

		private final String value;

		private final String code;

		WooriVirtualAcctStatus(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getCode() {
			return code;
		}
	}

	public enum WooriVirtualAcctReceiptCondition {
		NO_CONDITION("01", "No condition"),
		MORE_THAN_AMOUNT("02", "receipt more than amount"),
		MATCH_AMOUNT("03", "receipt only a match amount"),
		LESS_THAN_AMOUNT("04", "receipt less than amount"),
		ONE_TIME_NO_CONDITION("11", "Mã nộp dùng 1 lần không xét điều kiện số tiền"),
		ONE_TIME_MORE_THAN_AMOUNT("12", "Mã nộp dùng 1 lần chỉ thu số tiền nhiều hơn số tiền đăng ký"),
		ONE_TIME_MATCH_AMOUNT("13", "Mã nộp dùng 1 lần, chỉ thu đúng số tiền đăng ký"),
		ONE_TIME_LESS_THAN_AMOUNT("14", "Mã nộp tiền dùng 1 lần chỉ thu ít hơn số tiền đăng ký");;

		private final String description;

		private final String code;

		WooriVirtualAcctReceiptCondition(String code, String description) {
			this.code = code;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public String getCode() {
			return code;
		}
	}

	public enum WooriTaskIDCode {

		REQUEST("100", "Request"),
		RESPONSE("101", "Response");

		private final String description;

		private final String code;

		WooriTaskIDCode(String code, String description) {
			this.code = code;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public String getCode() {
			return code;
		}

	}

	public enum WooriDepositWithdrawIdentifier {

		CREDIT("11", "Giao dịch ghi có"),
		DEBIT("21", "Giao dịch ghi nợ"),
		OTHER("99", "Giao dịch khác");

		private final String description;

		private final String code;

		WooriDepositWithdrawIdentifier(String code, String description) {
			this.code = code;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public String getCode() {
			return code;
		}

	}

	public enum WooriReceiptDateCondition {

		C("C", "deposit date is limited from start date and time  to end date and time"),
		Y("Y", "deposit date is limited from start time to end time each day from start date to end date."),
		N("N", "deposit date is unlimited");

		private final String description;

		private final String code;

		WooriReceiptDateCondition(String code, String description) {
			this.code = code;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public String getCode() {
			return code;
		}

	}


}
