package com.onefin.ewallet.common.base.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.onefin.ewallet.common.base.errorhandler.RuntimeBadRequestException;

import java.util.stream.Stream;

public class OneFinEnum {

	public enum CurrencyCode {

		VND("VND"),
		USD("USD");

		private final String value;

		CurrencyCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static String fromText(String text) {
			for (CurrencyCode r : CurrencyCode.values()) {
				if (r.getValue().equals(text)) {
					return r.getValue();
				}
			}
			return VND.getValue();
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static Stream<CurrencyCode> stream() {
			return Stream.of(CurrencyCode.values());
		}

	}

	public enum NapasTransactionSource {
		SS("SS", "Soft Space"),
		OF("OF", "OneFin");


		private final String name;

		private final String value;

		NapasTransactionSource(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	public enum CMSMerchantTransactionSource {

		PAYMENT_GATEWAY_V1("PAYMENT_GATEWAY_V1", "payment gateway version 1"),
		PAYMENT_GATEWAY_V2("PAYMENT_GATEWAY_V3", "payment gateway version 3"),
		MERCHANT_UTILITY_V1_0_1("MERCHANT_UTILITY_V1.0.1", "merchant utility version 1.0.1");

		private final String name;

		private final String value;

		CMSMerchantTransactionSource(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static Stream<CMSMerchantTransactionSource> stream() {
			return Stream.of(CMSMerchantTransactionSource.values());
		}

	}

	public enum FeeModel {

		// Vietin Fee Type
		BENEFICIARY("BEN"),
		OUR("OUR"),

		// BVbank Fee Model
		B2B("B2B"),
		B2C("B2C");

		private final String value;

		FeeModel(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static FeeModel fromText(String text) {
			for (FeeModel r : FeeModel.values()) {
				if (r.getValue().equals(text)) {
					return r;
				}
			}
			throw new RuntimeBadRequestException();
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static Stream<FeeModel> stream() {
			return Stream.of(FeeModel.values());
		}

	}

	public enum CMSMerchantIBFTErrorCode {
		SUCCESS("00"),
		FAILED("07"),
		MAINTAINED_PARTNER("08"),
		AMOUNT_NOT_REACH_MINIMUM_LIMIT("21"),
		AMOUNT_OVER_MAXIMUM_LIMIT_PER_TRANS("22"),
		AMOUNT_OVER_MAXIMUM_LIMIT_PER_DATE("23"),
		TRANS_PENDING("45"),
		ORIGIN_TRANS_NOT_SUCCESS("48"),
		MERCHANT_TRANS_ID_DUPLICATED("57"),
		ORDER_ID_DUPLICATED("58"),
		BENEFICIARY_ACCOUNT_NOT_EXIST("79"),
		INVALID_RECEIVING_BANK_CODE("81"),
		TRANS_WAITING_FOR_APPROBATION("139"),
		TRANS_BEING_PROCESSED_BY_PROVIDER("140"),
		CHECK_QUERY_INFO_AGAIN("141"),
		SPENDING_ACCOUNT_NOT_ENOUGH_BALANCE("142"),
		UNACTIVE_PRODUCT_CODE("143"),
		INVALID_SIGNATURE("123"),
		UNKNOWN("95");

		private final String basecode;

		CMSMerchantIBFTErrorCode(String basecode) {
			this.basecode = basecode;
		}

		public String getBasecode() {
			return basecode;
		}

		@Override
		public String toString() {
			return this.basecode;
		}

		public static Stream<CMSMerchantIBFTErrorCode> stream() {
			return Stream.of(CMSMerchantIBFTErrorCode.values());
		}


	}


}
