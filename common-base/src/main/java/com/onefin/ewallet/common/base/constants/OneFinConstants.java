package com.onefin.ewallet.common.base.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.onefin.ewallet.common.base.errorhandler.RuntimeBadRequestException;
import com.onefin.ewallet.common.base.errorhandler.RuntimeInternalServerException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class OneFinConstants {

	/******************** Common constant **********************/
	public static final String UTF8 = "UTF-8";
	public static final String UTF8_1 = "UTF8";
	public static final String SHA1withRSA = "SHA1withRSA";
	public static final String AUTHORIZATION = "Authorization";
	public static final String DATE_FORMAT_TRANS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_MMddyy = "MMddyy";
	public static final String DATE_FORMAT_yyyyMMdd = "yyyy-MM-dd";
	public static final String DATE_FORMAT_ddMMyyyy = "ddMMyyyy";
	public static final String DATE_FORMAT_dd_MM_yyyy = "dd-MM-yyyy";
	public static final String DATE_FORMAT_dd_MM_yyyy_2 = "dd/MM/yyyy";
	public static final String DATE_FORMAT_HHmmss = "HHmmss";
	public static final String DATE_FORMAT_MMdd = "MMdd";
	public static final String DATE_FORMAT_yyyyMM = "yyyy-MM";
	public static final String DATE_FORMAT_HHmmssddMMyyyy = "HHmmssddMMyyyy";
	public static final String DATE_FORMAT_ddSMMSyyyy_HHTDmmTDss = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_yyyyMMDDHHmmss = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_ddMMyyyyHHmmss = "ddMMyyyyHHmmss";
	public static final String DATE_FORMAT_yyyyMMDD = "yyyyMMdd";
	public static final String DATE_FORMAT_yyyyMMddTHHmmssZ = "yyyyMMdd'T'HHmmss'Z'";
	public static final String DATE_FORMAT_yyyyMMddTHHmmssSSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	public static final String DATE_FORMAT_RSQL = "dd-MM-yyyy*HH:mm:ss";

	public static final String HO_CHI_MINH_TIME_ZONE = "Asia/Ho_Chi_Minh";
	public static final String CURRENCY_VND = "VND";

	/* SS transaction type */
	public static final String SPENDING_CHARGE = "SPENDING_CHARGE";

	public enum MpgsOperation {

		CAPTURE("CAPTURE"),
		AUTHORIZE("AUTHORIZE"),
		PAY("PAY"),
		VERIFY("VERIFY"),
		REFUND("REFUND"),
		DEBT_RECOVERY("DEBT_RECOVERY");

		private final String value;

		MpgsOperation(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum CardBrand {

		NAPAS("NAPAS"),
		VISA("VISA"),
		MASTERCARD("MASTERCARD"),
		JCB("JCB"),
		AMEX("AMEX"),
		UNIONPAY("UNIONPAY"),
		DINERSCLUB("DINERSCLUB"),
		DISCOVER("DISCOVER"),
		UNIPASS("UNIPASS"),
		ONEFIN("ONEFIN"),
		OTHER("OTHER");

		private final String value;

		CardBrand(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum NapasResult {

		SUCCESS("SUCCESS"),
		ERROR("ERROR"),
		PENDING("PENDING"),
		FAILURE("FAILURE");

		private final String value;

		NapasResult(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Stream<NapasResult> stream() {
			return Stream.of(NapasResult.values());
		}

	}

	public enum AscResult {

		SUCCESS("00"),
		ALREADY_SUCCESS("29");

		private final String value;

		AscResult(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Stream<AscResult> stream() {
			return Stream.of(AscResult.values());
		}

	}

	public enum BANK_TRANSFER_TYPE {

		CITAD_TTSP("CITAD_TTSP"),
		NAPAS_247("NAPAS_247"),
		VIETIN_INTERNAL("VIETIN_INTERNAL");

		private final String value;

		BANK_TRANSFER_TYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static BANK_TRANSFER_TYPE fromText(String text) {
			for (BANK_TRANSFER_TYPE r : BANK_TRANSFER_TYPE.values()) {
				if (r.getValue().equals(text)) {
					return r;
				}
			}
			throw new RuntimeBadRequestException();
		}

		public static Stream<BANK_TRANSFER_TYPE> stream() {
			return Stream.of(BANK_TRANSFER_TYPE.values());
		}

	}

	public enum PaymentGatewayPaymentMethod {
		ChiHo(100, "ChiHo", "Chi ho"), BankTransfer(99, "VIETQR", "VIETQR OneFin Hosted"), BankTransferAsc(98, "VIETQR", "VIETQR Merchant Hosted"), OneFinEwallet(11, "Ewallet", "OneFin Ewallet"), ATMNapas(10, "NAPAS", "Thẻ ghi nợ nội địa-ATM"), CBYS(5, "CYBS", "Thẻ quốc tế-Visa/Master/JCB");

		private int value; // Transaction method

		private String ssPaymentGateway;

		private String payName;

		PaymentGatewayPaymentMethod(int value, String ssPaymentGateway, String payName) {
			this.value = value;
			this.ssPaymentGateway = ssPaymentGateway;
			this.payName = payName;
		}

		public int getValue() {
			return this.value;
		}

		public String getSsPaymentGateway() {
			return this.ssPaymentGateway;
		}

		public String getPayName() {
			return this.payName;
		}

		public static Stream<PaymentGatewayPaymentMethod> stream() {
			return Stream.of(PaymentGatewayPaymentMethod.values());
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static PaymentGatewayPaymentMethod findByTransactionMethod(int data) {
			for (PaymentGatewayPaymentMethod r : PaymentGatewayPaymentMethod.values()) {
				if (r.getValue() == data) {
					return r;
				}
			}
			throw new RuntimeInternalServerException(String.format("Data %d not support for PaymentGatewayPaymentMethod", data));
		}

		public static Optional<PaymentGatewayPaymentMethod> findBySsPaymentGateway(String ssPaymentGateway) {
			return Arrays.stream(values()).filter(data -> data.getSsPaymentGateway().equals(ssPaymentGateway)).findFirst();
		}

		@Override
		public String toString() {
			return this.ssPaymentGateway;
		}
	}

	public enum LinkType {
		CARD, ACCOUNT;
	}

	public enum LANGUAGE {

		ENGLISH("en"),
		VIETNAMESE("vi");

		private final String value;

		LANGUAGE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static LANGUAGE fromText(String text) {
			for (LANGUAGE r : LANGUAGE.values()) {
				if (r.getValue().equals(text)) {
					return r;
				}
			}
			throw new RuntimeBadRequestException();
		}

		public static Stream<LANGUAGE> stream() {
			return Stream.of(LANGUAGE.values());
		}

	}

	public enum TokenState {

		ACTIVE("ACTIVE"),
		INACTIVE("INACTIVE"),
		EXPIRED("EXPIRED");

		private final String value;

		TokenState(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Stream<TokenState> stream() {
			return Stream.of(TokenState.values());
		}

	}

	public enum HolidayStatus {

		HOLIDAY("H"),
		WORK("W");

		private final String value;

		HolidayStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Stream<HolidayStatus> stream() {
			return Stream.of(HolidayStatus.values());
		}

	}

	public enum MerchantCategory {

		MASTER("master"),
		PAYDI("paydi"),
		ASC("asc");

		private final String value;

		MerchantCategory(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Stream<MerchantCategory> stream() {
			return Stream.of(MerchantCategory.values());
		}

	}

	public enum SoftSpaceChanelType {

		SPENDING_CHANNEL_TYPE_LOCAL_DEBIT("Local Debit/ATM Card", "SPENDING_CHANNEL_TYPE_LOCAL_DEBIT"),
		SPENDING_CHANNEL_TYPE_CREDIT_DEBIT_CARD("Direct Spending Credit/Debit", "SPENDING_CHANNEL_TYPE_CREDIT_DEBIT_CARD"),
		SPENDING_CHANNEL_TYPE_CREDIT_DEBIT_CARD_INT("Direct Spending Credit/Debit(INT)", "SPENDING_CHANNEL_TYPE_CREDIT_DEBIT_CARD_INT"),
		SPENDING_ECOMMERCE_QR_DYNAMIC("Ecommerce Dynamic QR Code", "SPENDING_ECOMMERCE_QR_DYNAMIC"),
		SPENDING_ECOMMERCE_LOGIN("Ecommerce Login", "SPENDING_ECOMMERCE_LOGIN"),
		SPENDING_CHANNEL_TYPE_THIRD_PARTY_BANK_PAYMENT("Third party bank payment", "SPENDING_CHANNEL_TYPE_THIRD_PARTY_BANK_PAYMENT");
		private final String name;
		private final String value;


		SoftSpaceChanelType(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public static Stream<SoftSpaceChanelType> stream() {
			return Stream.of(SoftSpaceChanelType.values());
		}

	}


	public enum SoftSpaceEwalletChanelType {

		WITHDRAWAL_OWN_CASA("Own casa withdrawal", "WITHDRAWAL_OWN_CASA"),
		TOPUP_CREDIT_DEBIT("Top-up via credit/debit card", "TOPUP_CREDIT_DEBIT"),
		FUND_TRANSFER_P2P("P2P fund transfer", "FUND_TRANSFER_P2P"),

		TOPUP_CASA("Top-up via bank account", "TOPUP_CASA"),
		SPENDING_IN_APP_PURCHASE("In-app purchase", "SPENDING_IN_APP_PURCHASE"),

		SPENDING_ECOMMERCE_QR_DYNAMIC("Ecommerce Dynamic QR Code", "SPENDING_ECOMMERCE_QR_DYNAMIC"),

		SPENDING_CHANNEL_TYPE_CREDIT_DEBIT_CARD("Direct Spending Credit/Debit", "SPENDING_CHANNEL_TYPE_CREDIT_DEBIT_CARD"),

		CASHBACK_REFEREE("Cashback for referee", "CASHBACK_REFEREE"),

		CASHBACK_REFERRER("Cashback for referrer", "CASHBACK_REFERRER"),
		SPENDING_MERCHANT_PRESENT_DYNAMIC("Direct Spending using merchant QR", "SPENDING_MERCHANT_PRESENT_DYNAMIC"),

		SPENDING_CUSTOMER_PRESENT_DYNAMIC("Direct Spending using customer QR", "SPENDING_CUSTOMER_PRESENT_DYNAMIC");
		private final String name;
		private final String value;


		SoftSpaceEwalletChanelType(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public static Stream<SoftSpaceEwalletChanelType> stream() {
			return Stream.of(SoftSpaceEwalletChanelType.values());
		}

	}

	public enum SoftSpaceTransType {

		SPENDING("Spending", "SPENDING"),
		REFUND("Refund", "REFUND");
		private final String name;
		private final String value;


		SoftSpaceTransType(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public static Stream<SoftSpaceTransType> stream() {
			return Stream.of(SoftSpaceTransType.values());
		}

	}

	public enum EmailStatus {

		DELIVERED("DELIVERED"),
		NOT_DELIVERED("NOT_DELIVERED"),
		FAILED_DELIVER("FAILED_DELIVER");

		private final String value;

		EmailStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Stream<EmailStatus> stream() {
			return Stream.of(EmailStatus.values());
		}

	}

	public enum ServiceType {
		PAYMENT_GATEWAY_ONEFIN_WALLET("PAYMENT_GATEWAY_ONEFIN_WALLET", ServiceTypeGroupCode.SOFTSPACE.getValue()),
		BANK_TRANSFER("PAYMENT_GATEWAY_VIETQR", ServiceTypeGroupCode.BANK_TRANSFER.getValue()),
		PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL("PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL", ServiceTypeGroupCode.PAYMENT_GATEWAY_NAPAS_CARD.getValue()),
		PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL_TEST("PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL_TEST", ServiceTypeGroupCode.PAYMENT_GATEWAY_NAPAS_CARD.getValue()),
		PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL1("PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL1", ServiceTypeGroupCode.PAYMENT_GATEWAY_NAPAS_CARD.getValue()),
		PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL2("PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL2", ServiceTypeGroupCode.PAYMENT_GATEWAY_NAPAS_CARD.getValue()),
		PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL3("PAYMENT_GATEWAY_NAPAS_CARD_ONEFINWL3", ServiceTypeGroupCode.PAYMENT_GATEWAY_NAPAS_CARD.getValue()),
		DIRECT_SPENDING_CREDIT_DEBIT("PAYMENT_GATEWAY_CREDIT_DEBIT_CARD_DOMESTIC", ServiceTypeGroupCode.DIRECT_SPENDING_CREDIT_DEBIT.getValue()),
		DIRECT_SPENDING_CREDIT_DEBIT_INT("PAYMENT_GATEWAY_CREDIT_DEBIT_CARD_INTERNATIONAL", ServiceTypeGroupCode.DIRECT_SPENDING_CREDIT_DEBIT.getValue()),
		FUND_TRANSFER_B2C("FUND_TRANSFER_B2C", ServiceTypeGroupCode.FUND_TRANSFER_B2C.getValue());

		private String code;

		private String serviceGroupCode;

		ServiceType(String code, String serviceGroupCode) {
			this.code = code;
			this.serviceGroupCode = serviceGroupCode;
		}

		public String getCode() {
			return this.code;
		}

		public String getServiceGroupCode() {
			return this.serviceGroupCode;
		}

		public static Stream<ServiceType> stream() {
			return Stream.of(ServiceType.values());
		}

		@Override
		public String toString() {
			return this.code;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static ServiceType findByCode(String code) {
			for (ServiceType r : ServiceType.values()) {
				if (r.getCode().equals(code)) {
					return r;
				}
			}
			throw new RuntimeInternalServerException(String.format("Data %s not support for ServiceType", code));
		}
	}

	public enum ServiceTypeGroupCode {
		BANK_TRANSFER("99"), SOFTSPACE("11"), LOCAL_DEBIT_ATM_CARD("10"), DIRECT_SPENDING_CREDIT_DEBIT("5"), COLLECTION("101"), FUND_TRANSFER_B2C("100"), PAYMENT_GATEWAY_NAPAS_CARD("PAYMENT_GATEWAY_NAPAS_CARD");

		private String code;

		ServiceTypeGroupCode(String code) {
			this.code = code;
		}

		public String getValue() {
			return this.code;
		}

		public static Stream<ServiceTypeGroupCode> stream() {
			return Stream.of(ServiceTypeGroupCode.values());
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static ServiceTypeGroupCode findByCode(String code) {
			for (ServiceTypeGroupCode r : ServiceTypeGroupCode.values()) {
				if (r.getValue().equals(code)) {
					return r;
				}
			}
			throw new RuntimeInternalServerException(String.format("Data %s not support for ServiceGroupCode", code));
		}
	}

	public enum SoftSpaceTransStatus {

		OPENED("Opened", "OPENED"),
		DECLINED("Declined", "DECLINED"),
		APPROVED("Approved", "APPROVED"),
		SETTLED("Settled", "SETTLED"),
		INITIATING("Initiating", "INITIATING"),
		INITIATED("Initiated", "INITIATED"),
		PROCESSING_VOID("Processing Void", "PROCESSING_VOID"),
		VOIDED("Voided", "VOIDED"),
		PROCESSING_REFUND("Processing Refund", "PROCESSING_REFUND"),
		REFUNDED("Refunded", "REFUNDED"),
		VOIDED_REFUND("Voided Refund", "VOIDED_REFUND"),
		PROCESSING_REVERSAL("Processing Reversal", "PROCESSING_REVERSAL"),
		REVERSED("Reversed", "REVERSED"),
		REVERSAL_ERROR("Reversal Error", "REVERSAL_ERROR"),
		CANCELLED("Cancelled", "CANCELLED"),
		FAILED("Failed", "FAILED"),
		PENDING_APPROVAL("Pending Approval", "PENDING_APPROVAL"),
		PENDING_CONFIRMATION("Pending Confirmation", "PENDING_CONFIRMATION"),
		PROCESSING_PREAUTH("Processing Preauth", "PROCESSING_PREAUTH"),
		AUTHORIZED("Authorized", "AUTHORIZED"),
		VOID_FAILED("Void Failed", "VOID_FAILED");

		private final String value;
		private final String name;

		SoftSpaceTransStatus(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public static Stream<SoftSpaceTransStatus> stream() {
			return Stream.of(SoftSpaceTransStatus.values());
		}

	}

	public enum IBFTProvider {

		BVBANK("BVBANK", "VCCB"),
		VIETINBANK("VIETINBANK", "CTG");

		private final String partnerCode;
		private final String bankCode;

		IBFTProvider(String partnerCode, String bankCode) {
			this.partnerCode = partnerCode;
			this.bankCode = bankCode;
		}

		public String getPartnerCode() {
			return partnerCode;
		}

		public String getBankCode() {
			return bankCode;
		}

		public static Stream<IBFTProvider> stream() {
			return Stream.of(IBFTProvider.values());
		}

		public static IBFTProvider findProviderFromPartnerCode(String partnerCode) {
			return stream().filter(e -> partnerCode.equals(e.getPartnerCode())).findFirst().orElse(null);
		}

		public static IBFTProvider findProviderFromBankCode(String bankCode) {
			return stream().filter(e -> bankCode.equals(e.getBankCode())).findFirst().orElse(null);
		}

	}

	/******************** Common constant **********************/

	/******************** Transaction status **********************/
	public static final String TRANS_SUCCESS = "SUCCESS"; // Success
	public static final String TRANS_ERROR = "ERROR"; // Error
	public static final String TRANS_PROCESSING = "PROCESSING"; // Transaction is processing, e.g. waiting response from partner (bank, napas ...)
	public static final String TRANS_PENDING = "PENDING"; // Waiting next action to complete the trx, e.g. pending otp
	public static final String TRANS_SETTLED = "SETTLED"; // Trx already settled with partner EOD
	public static final String TRANS_TIMEOUT = "TIMEOUT"; // Timeout
	public static final String TRANS_DISPUTE = "DISPUTE"; // Dispute trx, waiting settle with partner EOM
	public static final String TRANS_RECONSOLIDATE_SUCCESS = "RECONSO_SUCCESS"; // Success update transaction after, Thirdparty check
	public static final String TRANS_RECONSOLIDATE_ERROR = "RECONSO_ERROR"; // Success update transaction after, Thirdparty check
	public static final String TRANS_RECONCILE_01 = "RECONCILE_01"; //Chênh lệch, ghi nhận thất bại phía đối tác nhưng bank thành công - user for settlement
	public static final String TRANS_RECONCILE_02 = "RECONCILE_02"; //Chênh lệch, ghi nhận thành công phía đối tác nhưng bank fail - user for settlement
	public static final String TRANS_RECONCILE_03 = "RECONCILE_03"; //Chênh lệch, không ghi nhận phía đối tác nhưng	bank thành công - user for settlement
	public static final String TRANS_RECONCILE_04 = "RECONCILE_04"; //Chênh lệch, ghi nhận thành công phía đối tác nhưng không thấy bank gửi sang - user for settlement
	/******************** Transaction status **********************/

	/******************** Settlement status **********************/
	public static final String SETTLE_TRANS_SUCCESS = "SUCCESS"; // Success
	public static final String SETTLE_TRANS_ERROR = "ERROR"; // Error
	public static final String SETTLE_TRANS_PENDING = "PENDING"; // Waiting next action
	/******************** Settlement status **********************/

	/******************** Connector api operation name **********************/
	public static final String CHECK_LINKED = "CHECK_LINKED"; // Check card/account binded with ewallet
	public static final String CHECK_CARD_ACCOUNT = "CHECK_CARD_ACCOUNT"; // Check card/account valid
	public static final String TOKEN_ISSUER = "TOKEN_ISSUER"; // Link after verify success
	public static final String TOKEN_REVOKE = "TOKEN_REVOKE"; // Unlink card/account
	public static final String GET_USER_DETAILS = "GET_USER_DETAILS"; // Get user details
	public static final String CHECK_TRX_STATUS = "CHECK_TRX_STATUS"; // Check transaction status
	public static final String TOPUP_TOKEN = "TOPUP_TOKEN"; // Topup with token
	public static final String TOPUP_TOKEN_OTP = "TOPUP_TOKEN_OTP"; // Topup with token and otp
	public static final String WITHDRAW = "WITHDRAW"; // Withdraw
	public static final String REGISTER_ONLINE_PAYMENT = "REGISTER_ONLINE_PAYMENT"; // Register online payment
	public static final String TOKEN_REISSUE = "TOKEN_REISSUE"; // Refresh token
	public static final String TOKEN_ISSUER_TOPUP = "TOPUP_LINK"; // Topup and link card/account
	public static final String REFUND = "REFUND"; // Refund
	public static final String GET_MASTER_ACCOUNT_DETAILS = "MASTER_ACCOUNT"; // Get OneFin bank master account
	public static final String O_PAYBILL = "PAYBILL"; // Paybill
	public static final String BANK_TRANSFER = "BANK_TRANSFER"; // Disbursement
	public static final String PAYMENT = "PAYMENT"; // In case can't determine the payment type

	/******************** Connector api operation name **********************/

	/******************** Napas api operation name **********************/
	public static final String NAPAS_PURCHASE_WITH_RETURNED_TOKEN = "TOPUP_WITH_RETURNED_TOKEN";
	public static final String NAPAS_DELETE_TOKEN_APIOPERATION = "DELETE_TOKEN";
	public static final String NAPAS_TOPUP_WITH_TOKEN = "TOPUP_WITH_TOKEN";
	public static final String NAPAS_TOPUP_WITHOUT_TOKEN = "TOPUP_WITHOUT_TOKEN";
	public static final String NAPAS_RETRIEVE_TOKEN = "RETRIEVE_TOKEN";
	public static final String NAPAS_RETRIEVE_TRANSACTION = "RETRIEVE";
	public static final String NAPAS_REFUND = "REFUND";

	public static final String NAPAS_CASHOUT_ACCOUNT_INQUIRY = "ACCOUNT_INQUIRY";
	public static final String NAPAS_CASHOUT_ACCOUNT_TRANSFER = "ACCOUNT_TRANSFER";
	public static final String NAPAS_CASHOUT_TRANSACTION_INQUIRY = "TRANSACTION_INQUIRY";

	/******************** Napas api operation name **********************/

	/******************** Partner name **********************/
	public static final String PARTNER_NAPAS = "NAPAS";
	public static final String PARTNER_VIETINBANK = "VIETINBANK";

	public static final String PARTNER_VPBANK_MPGS = "VPBANK_MPGS";
	public static final String PARTNER_VIETINBANK_CBYS = "VIETINBANK_CBYS";
	public static final String PARTNER_VCB = "VCB";
	public static final String PARTNER_VNPAY = "VNPAY";
	public static final String PARTNER_BIDV = "BIDV";
	public static final String PARTNER_IOMEDIA = "IOMEDIA";
	public static final String PARTNER_IMEDIA = "IMEDIA";
	public static final String PARTNER_ONEFIN_TEST = "ONEFIN-TEST";
	public static final String PARTNER_PAYDI = "PAYDI";
	public static final String PARTNER_ASC = "ASC";
	public static final String PARTNER_CMS_RM = "CMS-RM";
	public static final String PARTNER_SOFTSPACE = "SS";
	public static final String PARTNER_BVBANK = "BVBANK";
	public static final String PARTNER_WOORI = "WOORI";

	/******************** Partner name **********************/

	/******************** Business domain **********************/
	public static final String CASH_OUT = "CASH_OUT";
	public static final String LINK_BANK = "LINK_BANK";
	public static final String VIRTUAL_ACCT = "VIRTUAL_ACCT";
	public static final String BVB_IBFT = "IBFT";
	public static final String BVB_TRANS_EXPORT = "TRANS_EXPORT";
	public static final String STATEMENT = "STATEMENT";
	public static final String PAYMENT_GATEWAY = "PAYMENT_GATEWAY";
	public static final String PAYMENT_GATEWAYWL1 = "PAYMENT_GATEWAY_WL1";
	public static final String PAYMENT_GATEWAYWL2 = "PAYMENT_GATEWAY_WL2";
	public static final String PAYMENT_GATEWAYWL3 = "PAYMENT_GATEWAY_WL3";
	public static final String AIRTIME_TOPUP = "AIRTIME_TOPUP";
	public static final String SMS_BRANDNAME = "SMS_BRANDNAME";
	public static final String SMARTPOS_MERCHANT = "SMARTPOS_MERCHANT";
	public static final String SOFTPOS_MERCHANT = "SOFTPOS_MERCHANT";
	public static final String PAYBILL = "PAYBILL";
	public static final String TOPUP = "TOPUP";

	public static final String BUYCARD = "BUYCARD";

	public static final String VTB_DISBURSEMENT = "DISBURSEMENT";

	// domain for bvb
	public static final String BVB_CODE_ACC = "VIRTUAL_ACCT_CODE_ACC";
	public static final String BVB_CODE_SYS = "VIRTUAL_ACCT_CODE_SYS";
	public static final String BVB_UNKNOWN = "UNKNOWN_ERROR_CODE";
	public static final String BVB_CODE_TRANSFER = "BANK_TRANSFER";

	/******************** Business domain **********************/

	/******************** Backup type **********************/
	public static final String BACKUP_REQUEST = "REQUEST";
	public static final String BACKUP_RESPONSE = "RESPONSE";

	/******************** Backup type **********************/

	/******************** Base Constant **********************/
	public static final String SET = "set";
	public static final String META = "Meta";
	public static final String NODE = "node";
	public static final String PARENT_PATH = "parentpath";
	public static final String UNDERSCORE = "_";
	public static final String SLASH = "/";
	public static final String PARENT_PATH_FIELD_NAME = "parentPath";
	public static final String CURRENT_VERSION = "current_version";
	public static final String OLD_VERSION = "old_version";
	public static final String GET = "get";
	public static final String ID = "id";
	public static final String UID = "uid";
	public static final String VERSION = "version";
	public static final String META_DATA = "metaData";
	public static final String ACTION_TYPE = "type";
	public static final String DETAIL_CACHE = "DetailCache";
	public static final String cvsSplitBy = ",";

	public static final String MERCHANT = "Merchant";

	/******************** Base Constant **********************/

	/******************** Common connector code **********************/
	public enum CommonConnectorCode {

		E_CONN_SUCCESS(CONN_SUCCESS, "Success"),
		E_CONN_PARTNER_INVALID_SIGNATURE(CONN_PARTNER_INVALID_SIGNATURE, "Invalid signature from partner"),
		E_CONN_PARTNER_ERROR_RESPONSE(CONN_PARTNER_ERROR_RESPONSE, "HTTP status from partner not equal OK"),
		E_CONN_PARTNER_INVALID_RESPONSE(CONN_PARTNER_INVALID_RESPONSE, "Invalid response from partner"),
		E_CONN_PARTNER_TIMEOUT_RESPONSE(CONN_PARTNER_TIMEOUT_RESPONSE, "Partner request timeout"),
		E_CONN_FAIL_TO_EXCUTE_VALIDATION_FUNCTION(CONN_FAIL_TO_EXCUTE_VALIDATION_FUNCTION, "Execute validation response fail"),
		E_CONN_INTERNAL_SERVER_ERROR(CONN_INTERNAL_SERVER_ERROR, "Internal server error, check with admin"),
		E_CONN_SERVICE_NOT_AVAILABLE(CONN_SERVICE_NOT_AVAILABLE, "Service is not available"),
		E_CONN_SERVICE_UNDER_MAINTENANCE(CONN_SERVICE_UNDER_MAINTENANCE, "Service is in maintenance"),
		E_CONN_BAD_REQUEST(CONN_BAD_REQUEST, "The request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications"),
		E_CONN_NOT_FOUND(CONN_NOT_FOUND, "The object referenced by the path does not exist."),
		E_CONN_CONFLICT(CONN_CONFLICT, "An attempt was made to create an object that already exists."),
		E_CONN_UNSUPPORTED_MEDIA_TYPE(CONN_UNSUPPORTED_MEDIA_TYPE, "The request entity has a media type which the server or resource does not support."),
		E_CONN_UNSUPPORTED_OPERATION_EXCEPTION(CONN_UNSUPPORTED_OPERATION_EXCEPTION, "Method not implemented yet"),
		E_CONN_AUTHENTICATE_TRANSACTION_FAILED(CONN_AUTHENTICATE_TRANSACTION_FAILED, "Get oauth2 token from napas fail");

		private final String code;
		private final String message;

		CommonConnectorCode(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		public static Stream<CommonConnectorCode> stream() {
			return Stream.of(CommonConnectorCode.values());
		}

	}

	public static final String STATUS_SUCCESS = "200.00";

	public static final String CONN_SUCCESS = "00";
	public static final String CONN_PARTNER_INVALID_SIGNATURE = "01";
	public static final String CONN_PARTNER_ERROR_RESPONSE = "02";
	public static final String CONN_PARTNER_INVALID_RESPONSE = "03";
	public static final String CONN_PARTNER_TIMEOUT_RESPONSE = "04";
	public static final String CONN_FAIL_TO_EXCUTE_VALIDATION_FUNCTION = "05";
	public static final String CONN_INTERNAL_SERVER_ERROR = "06";
	public static final String CONN_SERVICE_NOT_AVAILABLE = "07";
	public static final String CONN_SERVICE_UNDER_MAINTENANCE = "08";
	public static final String CONN_BAD_REQUEST = "09";
	public static final String CONN_NOT_FOUND = "10";
	public static final String CONN_CONFLICT = "11";
	public static final String CONN_UNSUPPORTED_MEDIA_TYPE = "12";
	public static final String CONN_UNSUPPORTED_OPERATION_EXCEPTION = "13";
	public static final String CONN_AUTHENTICATE_TRANSACTION_FAILED = "14";
	/******************** Common connector code **********************/

	/******************** Menu client type **********************/
	public static final String MENU_SERVICE_PAYBILL = "PAYBILL"; // Menu Pay Bill
	public static final String MENU_SERVICE_TELCO = "TELCO"; // Menu Mobile Topup
	/******************** Menu client type **********************/

	// public static final String BILL_SUCCESS = "00";

	/******************** Lazy list **********************/

	public enum OneFinTransactionStatus {
		SUCCESS("SUCCESS", TRANS_SUCCESS),
		SETTLED("SETTLED", TRANS_SETTLED),
		ERROR("ERROR", TRANS_ERROR),
		PROCESSING("PROCESSING", TRANS_PROCESSING),
		PENDING("PENDING", TRANS_PENDING),
		TIMEOUT("TMEOUT", TRANS_TIMEOUT),
		DISPUTE("DISPUTE", TRANS_DISPUTE),
		RECONSOLIDATE_SUCCESS("RECONSOLIDATE_SUCCESS", TRANS_RECONSOLIDATE_SUCCESS),
		RECONSOLIDATE_ERROR("RECONSOLIDATE_ERROR", TRANS_RECONSOLIDATE_ERROR),
		RECONCILE_01("RECONCILE_01", TRANS_RECONCILE_01),
		RECONCILE_02("RECONCILE_02", TRANS_RECONCILE_02),
		RECONCILE_03("RECONCILE_03", TRANS_RECONCILE_03),
		RECONCILE_04("RECONCILE_04", TRANS_RECONCILE_04);

		private final String name;

		private final String value;

		OneFinTransactionStatus(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static OneFinTransactionStatus fromText(String text) {
			for (OneFinTransactionStatus r : OneFinTransactionStatus.values()) {
				if (r.getValue().equals(text)) {
					return r;
				}
			}
			throw new RuntimeInternalServerException(String.format("Data %s not support for enum OneFinTransactionStatus", text));
		}


		public static Stream<OneFinTransactionStatus> stream() {
			return Stream.of(OneFinTransactionStatus.values());
		}
	}

	public enum NapasEwApiOperation {
		PURCHASE_WITH_RETURNED_TOKEN("PURCHASE WITH RETURNED TOKEN", NAPAS_PURCHASE_WITH_RETURNED_TOKEN),
		DELETE_TOKEN_APIOPERATION("PURCHASE WITH RETURNED TOKEN", NAPAS_PURCHASE_WITH_RETURNED_TOKEN),
		TOPUP_WITH_TOKEN("TOPUP WITH TOKEN", NAPAS_TOPUP_WITH_TOKEN),
		TOPUP_WITHOUT_TOKEN("TOPUP WITHOUT TOKEN", NAPAS_TOPUP_WITHOUT_TOKEN),
		REFUND("REFUND", NAPAS_REFUND);

		private final String name;

		private final String value;

		NapasEwApiOperation(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static NapasEwApiOperation fromText(String text) {
			for (NapasEwApiOperation r : NapasEwApiOperation.values()) {
				if (r.getValue().equals(text)) {
					return r;
				}
			}
			throw new RuntimeInternalServerException(String.format("Data %s not support for enum NapasEwApiOperation", text));
		}


		public static Stream<NapasEwApiOperation> stream() {
			return Stream.of(NapasEwApiOperation.values());
		}
	}

	public enum BankListQrService {
		CTG("VietinBank", "970415", "CTG", "01201001", "VIETINBANK"),
		VCCB("Bản Việt Bank", "970454", "VCCB", "79327001", "BVBANK"),
		WOORI("Woori Bank", "970457", WooriConstants.WOORI_BANK_CODE, "01663001", BankConstants.PARTNER_WOORI);
		private final String name;

		private final String bankId;

		private final String bankCode;

		private final String citad;

		private final String partnerCode;

		BankListQrService(String name, String bankId, String bankCode, String citad, String partnerCode) {
			this.name = name;
			this.bankId = bankId;
			this.bankCode = bankCode;
			this.citad = citad;
			this.partnerCode = partnerCode;
		}

		public String getName() {
			return this.name;
		}

		public String getBankId() {
			return this.bankId;
		}

		public String getBankCode() {
			return this.bankCode;
		}

		public String getCitad() {
			return citad;
		}

		public String getPartnerCode() {
			return partnerCode;
		}

		public static Stream<BankListQrService> stream() {
			return Stream.of(BankListQrService.values());
		}
	}

	public enum PaymentGatewayStatusId {
		APPROVED("Approved", "100"),
		OPENED("Opened", "106"),
		DECLINED("Declined", "102"),
		SETTLED("Settled", "104"),
		CANCELLED("Cancelled", "105"),
		VOIDED("Void", "107");

		private final String name;

		private final String value;

		PaymentGatewayStatusId(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static PaymentGatewayStatusId fromText(String text) {
			for (PaymentGatewayStatusId r : PaymentGatewayStatusId.values()) {
				if (r.getValue().equals(text)) {
					return r;
				}
			}
			throw new RuntimeInternalServerException(String.format("Data %s not support for enum PaymentGatewayStatusId", text));
		}


		public static Stream<PaymentGatewayStatusId> stream() {
			return Stream.of(PaymentGatewayStatusId.values());
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	public enum LazyListVirtualAccountTransStatus {
		TRANS_PENDING_STATUS(TRANS_PENDING, TRANS_PENDING),
		TRANS_TIMEOUT_STATUS(TRANS_TIMEOUT, TRANS_TIMEOUT),
		TRANS_SUCCESS_STATUS(TRANS_SUCCESS, TRANS_SUCCESS),
		TRANS_DISPUTE_STATUS(TRANS_DISPUTE, TRANS_DISPUTE);

		private final String name;

		private final String value;

		LazyListVirtualAccountTransStatus(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}


		public static Stream<LazyListVirtualAccountTransStatus> stream() {
			return Stream.of(LazyListVirtualAccountTransStatus.values());
		}
	}

	public enum LazyListPartnerName {
		E_PARTNER_NAPAS("Napas", PARTNER_NAPAS),
		E_PARTNER_Imedia("Imedia", PARTNER_IMEDIA),

		//		E_PARTNER_VIETINBANK("VietinBank", PARTNER_VIETINBANK),
		E_PARTNER_VNPAY("VNPay", PARTNER_VNPAY);
//		E_PARTNER_PAYDI("Paydi", PARTNER_PAYDI);

		private final String name;

		private final String value;

		LazyListPartnerName(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}


		public static Stream<LazyListPartnerName> stream() {
			return Stream.of(LazyListPartnerName.values());
		}
	}

	public enum LazyListDomainName {
		//		E_LINK_BANK("Link bank", LINK_BANK),
//		E_PAYMENT_GATEWAYWL1("WL1", PAYMENT_GATEWAYWL1),
//		E_PAYMENT_GATEWAYWL2("WL2", PAYMENT_GATEWAYWL2),
//		E_PAYMENT_GATEWAYWL3("WL3", PAYMENT_GATEWAYWL3),
		E_AIRTIME_TOPUP("Airtime topup", AIRTIME_TOPUP),
		E_IMEDIA_PAYBILL("Paybill", PAYBILL),
		//E_IMEDIA_TOPUP("Airtime_Topup", AIRTIME_TOPUP),
		E_SMS_BRANDNAME("Sms brandname", SMS_BRANDNAME);


//		E_SMARTPOS_MERCHANT("Smart POS", SMARTPOS_MERCHANT),
//		E_SOFTPOS_MERCHANT("Soft POS", SOFTPOS_MERCHANT),
//		E_PAYBILL("Paybill", PAYBILL);

		private final String name;

		private final String value;

		LazyListDomainName(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}


		public static Stream<LazyListDomainName> stream() {
			return Stream.of(LazyListDomainName.values());
		}
	}

	public enum NapasCeApiOperation {
		PURCHASE_WITH_RETURNED_TOKEN("PURCHASE WITH RETURNED TOKEN", NAPAS_PURCHASE_WITH_RETURNED_TOKEN),
		DELETE_TOKEN_APIOPERATION("PURCHASE WITH RETURNED TOKEN", NAPAS_PURCHASE_WITH_RETURNED_TOKEN),
		TOPUP_WITH_TOKEN("TOPUP WITH TOKEN", NAPAS_TOPUP_WITH_TOKEN),
		TOPUP_WITHOUT_TOKEN("TOPUP WITHOUT TOKEN", NAPAS_TOPUP_WITHOUT_TOKEN),
		ACCOUNT_INQUIRY("ACCOUNT INQUIRY", NAPAS_CASHOUT_ACCOUNT_INQUIRY),
		TRANSACTION_INQUIRY("TRANSACTION_INQUIRY", NAPAS_CASHOUT_TRANSACTION_INQUIRY),
		REFUND("REFUND", NAPAS_REFUND);
		private final String name;

		private final String value;

		NapasCeApiOperation(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static NapasCeApiOperation fromText(String text) {
			for (NapasCeApiOperation r : NapasCeApiOperation.values()) {
				if (r.getValue().equals(text)) {
					return r;
				}
			}
			throw new RuntimeInternalServerException(String.format("Data %s not support for enum NapasEwApiOperation", text));
		}


		public static Stream<NapasCeApiOperation> stream() {
			return Stream.of(
					NapasCeApiOperation.values());
		}
	}

	public enum JobStatus {
		RESUMED_STATUS("Resume Job", "RESUMED"),
		PAUSED_STATUS("Pause Job", "PAUSED"),
		SCHEDULED_STATUS("Schedule Job", "SCHEDULED");

		private final String name;

		private final String value;

		JobStatus(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static JobStatus fromText(String text) {
			for (JobStatus r : JobStatus.values()) {
				if (r.getValue().equals(text)) {
					return r;
				}
			}
			throw new RuntimeInternalServerException(String.format("Data %s not support for enum NapasEwApiOperation", text));
		}


		public static Stream<JobStatus> stream() {
			return Stream.of(JobStatus.values());
		}

	}

	public enum AscChannelType {
		VDT("VDT"),
		CTT("CTT");

		private final String value;

		AscChannelType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static AscChannelType fromValue(String value) {
			for (AscChannelType type : AscChannelType.values()) {
				if (type.value.equals(value)) {
					return type;
				}
			}
			return null; // Or throw an exception indicating an invalid value
		}

		public static Stream<AscChannelType> stream() {
			return Stream.of(AscChannelType.values());
		}
	}

	public enum AscPaymentMethod {
		ATM("ATM"),
		VIETQR("VietQR"),
		VISA_MASTER_JCB("Visa/Master/JCB");

		private final String value;

		AscPaymentMethod(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static AscPaymentMethod fromValue(String value) {
			for (AscPaymentMethod type : AscPaymentMethod.values()) {
				if (type.value.equals(value)) {
					return type;
				}
			}
			return null; // Or throw an exception indicating an invalid value
		}

		public static Stream<AscPaymentMethod> stream() {
			return Stream.of(AscPaymentMethod.values());
		}
	}


	public enum CrossCheckStatus {

		MATCHED("00"),
		OF_NOT_PROVIDER_HAVE("01"),

		OF_HAVE_PROVIDER_NOT("02"),
		NOT_MATCHED("03");

		private final String value;

		CrossCheckStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Stream<CrossCheckStatus> stream() {
			return Stream.of(CrossCheckStatus.values());
		}

	}

	/******************** Lazy list **********************/

}
