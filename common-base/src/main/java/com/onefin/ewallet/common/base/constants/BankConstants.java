package com.onefin.ewallet.common.base.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.poi.ss.usermodel.CellType;

import java.util.stream.Stream;

public class BankConstants extends OneFinConstants {

	public static final String BVB_REQUEST_STATUS_SUCCESS = "00";

	public static final String BVB_REQUEST_STATUS_FAILED = "01";

	public static final String BVB_REQUEST_STATUS_WRONG_SIGNATURE = "52";

	public static final String BVB_ACCOUNT_NOT_FOUND_ERROR_CODE = "A001";

	public static final String BVB_ACCOUNT_ALREADY_EXIST_ERROR_CODE = "A004";

	public static final String BVB_ACCOUNT_STATUS_INVALID_ERROR_CODE = "ST001";

	public static final String BVB_ACCOUNT_STATUS_INVALID_ERROR_CODE_OPEN = "ST001O";

	public static final String BVB_ACCOUNT_STATUS_INVALID_ERROR_CODE_CLOSE = "ST001C";

	public static final String INVALID_PARAM_REQUEST = "PR001";

	public static final String SYS_INTERNAL_ERROR = "SY001";

	public static final String DUPLICATE_REQUESTID = "2006";

	public static final String DUPLICATE_TRANSACTION_IBFT = "94";

	public static final String DUPLICATE_TRANSACTIONS = "99";

	public static final String BVB_UNKNOWN_ERROR_CODE = "U/N";

	public static final String BVB_DATE_FORMAT = "dd-MM-yyyy hh:mm:ss:SSS";

	public static final String BVB_TRANSACTIONS_DATE_FORMAT = "hh:mm:ss dd/MM/yyyy";

	public static final String BVB_BATCH_DATE_FORMAT = "dd-MM-yyyy hh:mm:ss.SSS";

	public static final String BVB_BACKUP_CREATE_PREFIX = "create";

	public static final String BVB_BACKUP_CLOSE_PREFIX = "close";

	public static final String BVB_BACKUP_VIEW_DETAIL_PREFIX = "viewAccountDetail";

	public static final String BVB_BACKUP_FIND_ACCOUNT_PREFIX = "findAccounts";

	public static final String BVB_BACKUP_UPDATE_ACCOUNT_PREFIX = "updateAccount";

	public static final String BVB_BACKUP_REOPEN_PREFIX = "reOpenAccount";

	public static final String BVB_BACKUP_GET_CALLBACK_PREFIX = "getCallbackData";

	public static final String BVB_BACKUP_SEARCH_TRANS_PREFIX = "searchTransactionByAccount";

	public static final String BVB_BACKUP_VIRTUAL_TRANS_PREFIX = "virtual-transaction";

	public static final String BVB_BACKUP_CALLBACK_PREFIX = "callBackApiHandle";

	public static final String BVB_BACKUP_CALLBACK_BATCH_PREFIX = "callBackApiBatchHandle";

	public static final String BVB_IBFT_BACKUP_QUERY_STATUS_PREFIX = "queryStatus";

	public static final String BVB_IBFT_BACKUP_INQUIRY_PREFIX = "inquiry";

	public static final String BVB_IBFT_BACKUP_FUND_TRANSFER_PREFIX = "fundTransfer";

	public static final String BVB_IBFT_BACKUP_INQUIRY_ESCROW_ACCOUNT_PREFIX = "inquiryEscrowAccount";

	public static final String BVB_IBFT_BACKUP_UPLOAD_RECONCILIATION_PREFIX = "uploadReconciliation";

	/******************** Backup type **********************/
	public static final String BACKUP_HEADER = "HEADER";

	public static final String BVB_BANK_CODE = "VCCB";


	public enum BVBAccType {
		ONETIME("O"),
		MANYTIME("M");

		private final String value;

		BVBAccType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static Stream<BVBAccType> stream() {
			return Stream.of(BVBAccType.values());
		}
	}

	public enum BVBTransMsgType {
		INDIVIDUAL("I"),
		BATCH("B");

		private final String value;

		BVBTransMsgType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static Stream<BVBTransMsgType> stream() {
			return Stream.of(BVBTransMsgType.values());
		}
	}

	public enum BVBTransType {
		CREDIT("C"),
		DEBIT("D");

		private final String value;

		BVBTransType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static Stream<BVBTransType> stream() {
			return Stream.of(BVBTransType.values());
		}
	}

	public enum BVBVirtualAcctStatus {
		OPEN("O"), CLOSE("C");

		private final String value;


		BVBVirtualAcctStatus(String o) {
			value = o;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static Stream<BVBVirtualAcctStatus> stream() {
			return Stream.of(BVBVirtualAcctStatus.values());
		}
	}

	public enum CollectionVirtualAcctStatus {
		OPEN("O"), CLOSE("C");

		private final String value;


		CollectionVirtualAcctStatus(String o) {
			value = o;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static Stream<CollectionVirtualAcctStatus> stream() {
			return Stream.of(CollectionVirtualAcctStatus.values());
		}
	}

	public enum CurrencyCode {

		VND("VND");

		private final String value;

		CurrencyCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		public static String fromText(String text) {
			for (BankConstants.CurrencyCode r : BankConstants.CurrencyCode.values()) {
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

	public enum BVBVirtualAcctErrorCode {
		ACCT_NOT_FOUND(PARTNER_BVBANK, BVB_CODE_ACC, BVB_ACCOUNT_NOT_FOUND_ERROR_CODE),
		ACCT_ALREADY_EXIST(PARTNER_BVBANK, BVB_CODE_ACC, BVB_ACCOUNT_ALREADY_EXIST_ERROR_CODE),
		INVALID_STATUS(PARTNER_BVBANK, BVB_CODE_SYS, BVB_ACCOUNT_STATUS_INVALID_ERROR_CODE),
		INVALID_PARAM_REQUEST(PARTNER_BVBANK, BVB_CODE_SYS, BankConstants.INVALID_PARAM_REQUEST),
		SYS_INTERNAL_ERROR(PARTNER_BVBANK, BVB_CODE_SYS, BankConstants.SYS_INTERNAL_ERROR),
		UNKNOWN_ERROR(PARTNER_BVBANK, BVB_UNKNOWN, BVB_UNKNOWN_ERROR_CODE),
		QUERY_SUCCESS(PARTNER_BVBANK, VIRTUAL_ACCT, BVB_REQUEST_STATUS_SUCCESS),
		QUERY_FAILED(PARTNER_BVBANK, VIRTUAL_ACCT, BVB_REQUEST_STATUS_FAILED),
		WRONG_SIGNATURE(PARTNER_BVBANK, VIRTUAL_ACCT, BVB_REQUEST_STATUS_WRONG_SIGNATURE),
		INVALID_AMOUNT(OneFinConstants.PARTNER_VCB, "LINK_BANK_CARD", "INVALID_AMOUNT");

		private final String partnerCode;

		private final String domainCode;

		private final String code;


		BVBVirtualAcctErrorCode(String partnerCode, String domainCode, String code) {
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

	public enum BVBIBFTTransStatus {
		PENDING("PENDING", "Pending", "Chờ đối soát", OneFinConstants.TRANS_PENDING),
		SUCCESS("SUCCESS", "Success", "Thành công", OneFinConstants.TRANS_SUCCESS),
		FAILED("FAILED", "Failed", "Thất bại", "FAILED");

		private final String name;

		private final String value;

		private final String description;

		private final String refValue;

		BVBIBFTTransStatus(String name, String value, String description, String refValue) {
			this.name = name;
			this.value = value;
			this.description = description;
			this.refValue = refValue;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}

		public String getRefValue() {
			return refValue;
		}
	}

	public enum BVBIBFTErrorCodeFromOnefin {
		WRONG_SIGNATURE(PARTNER_BVBANK, VIRTUAL_ACCT, BVB_REQUEST_STATUS_WRONG_SIGNATURE),
		INVALID_PARAM(PARTNER_BVBANK, BVB_CODE_SYS, INVALID_PARAM_REQUEST),
		DUPLICATE_TRANSFER(PARTNER_BVBANK, BVB_CODE_TRANSFER, DUPLICATE_REQUESTID);

		private final String partnerCode;

		private final String domainCode;

		private final String code;


		BVBIBFTErrorCodeFromOnefin(String partnerCode, String domainCode, String code) {
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

	public enum BVBIBFTErrorCode {

		// COMMON ERROR CODE
		TRANSACTION_PENDING("0", "Hệ thống đang xử lý giao dịch. Gọi querystatus để kiểm tra trạng thái giao dịch.", BVBIBFTTransStatus.PENDING.getRefValue()),
		TRANSACTION_SUCCESS("1", "Thành công", BVBIBFTTransStatus.SUCCESS.getRefValue()),
		INVALID_TIME_IN_REQUEST("2003", "Tham số time trong request không hợp lệ", BVBIBFTTransStatus.FAILED.getRefValue()),
		DUPLICATE_REQUESTID("2006", "Trùng requestID", BVBIBFTTransStatus.FAILED.getRefValue()),
		INVALID_SIGNATURE("2008", "Chữ ký số không hợp lệ (Null/empty)", BVBIBFTTransStatus.FAILED.getRefValue()),
		WRONG_SIGNATURE("2009", "Sai chữ ký số", BVBIBFTTransStatus.FAILED.getRefValue()),

		// TRANSACTION STATUS CHECKING CODE
		INVALID_REFERENCE_TRANSACTION_ID("2109", "Mã giao dịch tham chiếu không hợp lệ", BVBIBFTTransStatus.FAILED.getRefValue()),
		NOT_FOUND_REFERENCE_TRANSACTION("2110", "Không tìm thấy giao dịch tham chiếu", BVBIBFTTransStatus.FAILED.getRefValue()),

		// BALANCE CHECKING CODE
		INVALID_ACCOUNT_NUMBER("2151", "Số tài khoản không hợp lệ (Null/Empty)", BVBIBFTTransStatus.FAILED.getRefValue()),

		// IBFT ACCOUNT INQUIRY
		INVALID_ACCOUNT_CARD_NUMBER("2152", "Số tài khoản và số thẻ không hợp lệ (Null/Empty)", BVBIBFTTransStatus.FAILED.getRefValue()),
		INVALID_BANK_CODE("2153", "Mã ngân hàng không hợp lệ (Null/Empty)", BVBIBFTTransStatus.FAILED.getRefValue()),
		INVALID_ONUS_INFO("2154", "Thông tin onus không hợp lệ (Null/Empty)", BVBIBFTTransStatus.FAILED.getRefValue()),

		// RESPONSE CODE NAPAS SUPPORT
		REFER_TO_ISSUING_BANK("02", "Lỗi liên quan ngân hàng phát hành (Refer to issuing bank)", BVBIBFTTransStatus.FAILED.getRefValue()),
		DUPLICATE_TRANSACTION_IBFT("94", "Giao dịch IBFT bị trùng (Duplicate Transaction IBFT)", BVBIBFTTransStatus.FAILED.getRefValue()),
		DUPLICATE_TRANSACTIONS("99", "Giao dịch bị trùng (Duplicate Transaction)", BVBIBFTTransStatus.FAILED.getRefValue()),
		UNKNOWN_ERROR("XX", "Lỗi không xác định", BVBIBFTTransStatus.FAILED.getRefValue());

		private final String errorCode;

		private final String description;

		private final String transStatus;

		BVBIBFTErrorCode(String errorCode, String description, String transStatus) {
			this.errorCode = errorCode;
			this.description = description;
			this.transStatus = transStatus;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public String getDescription() {
			return description;
		}

		public String getTransStatus() {
			return transStatus;
		}

		public static BVBIBFTErrorCode findByErrorCode(String errorCode) {
			for (BVBIBFTErrorCode direction : values()) {
				if (direction.getErrorCode().equalsIgnoreCase(errorCode)) {
					return direction;
				}
			}
			return UNKNOWN_ERROR;
		}


	}

	public enum BVBExcelReadingForNapasBinEnum {
		STT("STT", 0, CellType.NUMERIC),
		BANK("Ngân hàng", 1, CellType.STRING),
		SHORT_NAME("Tên viết tắt/ Tên thương mại", 2, CellType.STRING),
		BANK_ID("BANK ID", 3, CellType.STRING),
		BEN_ID("BEN ID", 4, CellType.STRING),
		TRANS_MODEL_CARD("Mô hình chuyển - thẻ", 5, CellType.STRING),
		TRANS_MODEL_ACC("Mô hình chuyển - tk", 6, CellType.STRING),
		RECV_MODEL_CARD("Mô hình nhận - thẻ", 7, CellType.STRING),
		RECV_MODEL_ACC("Mô hình nhận - tk", 8, CellType.STRING),
		BIN("BIN thụ hưởng", 9, CellType.STRING),
		CARD_NAME("Thương hiệu thẻ", 10, CellType.STRING),
		CARD_LENGTH("Độ dài thẻ", 11, CellType.NUMERIC),
		CITAD_COD("Citad", 12, CellType.STRING),
		IS_CUSTOM("Custom citad", 13, CellType.STRING),
		;


		private final String field;

		private final int column;

		private final CellType type;

		BVBExcelReadingForNapasBinEnum(String field, int column, CellType type) {
			this.field = field;
			this.column = column;
			this.type = type;
		}

		public String getField() {
			return field;
		}

		public int getColumn() {
			return column;
		}

		public CellType getType() {
			return type;
		}

		public static Stream<BVBExcelReadingForNapasBinEnum> stream() {
			return Stream.of(BVBExcelReadingForNapasBinEnum.values());
		}
	}

	public enum ResponseStatus {

		SUCCESS("SUCCESS", "1"),
		FAIL("FAIL", "-1");

		private final String status;

		private final String statusCode;

		ResponseStatus(String status, String statusCode) {
			this.status = status;
			this.statusCode = statusCode;
		}

		public String getStatus() {
			return status;
		}

		public String getStatusCode() {
			return statusCode;
		}
	}

	public enum BVBonus {
		INTERNAL("1", "Nội bộ BVB"),
		EXTERNAL("0", "Các bank khác BVB");

		private final String value;

		private final String description;

		BVBonus(String value, String description) {
			this.value = value;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public String getValue() {
			return value;
		}
	}

	public enum BVBFeeModel {
		B2C("B2C", "B2C"),
		B2B("B2B", "B2B");

		private final String value;

		private final String description;

		BVBFeeModel(String value, String description) {
			this.value = value;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public String getValue() {
			return value;
		}

		public static Stream<BVBFeeModel> stream() {
			return Stream.of(BVBFeeModel.values());
		}
	}
}
