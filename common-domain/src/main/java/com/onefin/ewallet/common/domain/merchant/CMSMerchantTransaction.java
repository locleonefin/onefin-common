package com.onefin.ewallet.common.domain.merchant;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.bank.transfer.BankTransferChildRecords;
import com.onefin.ewallet.common.domain.base.StringListConverter;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Data
@Entity
@Table(name = "TRANSACTION_DATA")
public class CMSMerchantTransaction implements Serializable {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)", name = "ID")
	private UUID id;

	@Column(name = "SOURCE")
	@NotNull(message = "source must not be null")
	private String source;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = true, updatable = false, name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "VERSION", nullable = false, length = 11)
	private int version;

	@Column(name = "ACCOUNT_HOLDER_NAME")
	private String accountHolderName;

	@Column(name = "ACCOUNT_NO", length = 50)
	private String accountNo;

	@Column(name = "ACQUIRER_FLAT_RATE_AMOUNT", length = 20)
	private BigInteger acquirerFlatRateAmount;

	@Column(name = "ACQUIRER_MDR")
	private String acquirerMDR;

	@Column(name = "ACQUIRER_MODEL")
	private String acquirerModel;

	@Column(name = "ACQUIRER_NAME")
	private String acquirerName;

	@Column(name = "ACQUIRER_PARTNER_ID")
	private String acquirerPartnerID;

	@Column(name = "ACQUIRER_TRX_ID")
	private String acquirerTrxID;

	@Column(name = "ALTITUDE")
	private String altitude;

	@Column(name = "AMOUNT_AUTHORISED", length = 20)
	@NotNull(message = "amount authorised is empty")
	@Min(value = 1, message = "Min amount is 1 VND") // amount
	private BigInteger amountAuthorised;

	@Column(name = "TOTAL_AMOUNT", length = 20)
	@Min(value = 1, message = "Min total amount is 1 VND") // amount
	private BigInteger totalAmount;

	@Column(name = "AMOUNT_CHARGES", length = 20)
	@Min(value = 0, message = "Min amount is 0 VND") // processing fee
	private BigInteger amountCharges;

	@Column(name = "APPLICATION_LABEL")
	private String applicationLabel;

	@Column(name = "APPROVAL_CODE")
	private String approvalCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "APPROVED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

	@Column(name = "AUTH_CODE", length = 20)
	private String authCode;

	@Column(name = "BANK_ID")
	private String bankId;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "BILLING_PRODUCT")
	private String billingProduct;

	@Column(name = "BUSINESS_NAME")
	private String businessName;

	@Column(name = "BUSINESS_REGISTRATION_NUMBER")
	private String businessRegistrationNumber;

	@Column(name = "CARD_EXPIRY")
	private String cardExpiry;

	@Column(name = "CARD_ID")
	private String cardId;

	@Column(name = "CARD_NO")
	private String cardNo;

	@Column(name = "CARD_HOLDER_NAME")
	private String cardHolderName;


	@Column(name = "CARD_TYPE_ID")
	private String cardTypeId;

	@Column(name = "COST_BEARER")
	private String costBearer;

	@Column(name = "SERVICE_TYPE")
	private String serviceType;

	@Column(name = "GATEWAY_CALLBACK_RECEIVED")
	private Boolean gatewayCallbackReceived;

	@Lob
	@Column(name = "IDENTIFICATION_IMAGE")
	private String identificationImage;

	@Column(name = "IDENTIFICATION_NO")
	private String identificationNo;

	@Column(name = "IDENTIFICATION_TYPE")
	private String identificationType;

	@Column(name = "INVOICE_NO")
	private String invoiceNo;

	@Column(name = "ISSUER_FLAT_RATE_AMOUNT", length = 20)
	private BigInteger issuerFlatRateAmount;

	@Column(name = "ISSUER_MDR")
	private String issuerMDR;

	@Column(name = "ISSUER_MODEL")
	private String issuerModel;

	@Column(name = "ISSUER_NAME")
	private String issuerName;

	@Column(name = "ISSUER_PARTNER_ID")
	private String issuerPartnerId;

	@Column(name = "ISSUER_TRX_ID")
	private String issuerTrxId;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "MERCHANT_ACCEPTANCE_ID")
	private String merchantAcceptanceId;

	@Column(name = "MERCHANT_CHARGE_POLICY")
	private String merchantChargePolicy;

	@Column(name = "MERCHANT_CODE")
	@NotNull(message = "merchant code must not be null")
	@NotEmpty(message = "Empty merchant code") // merchantCode
	private String merchantCode;

	@Column(name = "MERCHANT_FLAT_AMOUNT", length = 20)
	private BigInteger merchantFlatAmount;

	@Column(name = "MERCHANT_MDR_VALUE")
	private String merchantMDRValue;

	@Column(name = "MERCHANT_NAME")
	private String merchantName;

	@Column(name = "MID")
	private String mid;

	@Column(name = "ORI_TRX_ID")
	private String oriTrxId;

	@Column(name = "PARTNER_ID")
	private String partnerId;

	@Column(name = "PARTNER_NAME")
	private String partnerName;

	@Column(name = "PAYER_EMAIL")
	private String payerEmail;

	@Column(name = "PAYER_MOBILE_NO")
	private String payerMobileNo;

	@Column(name = "PAYMENT_CHANNEL")
	private String paymentChannel;

	@Column(name = "PROVIDER")// paymentGateway
	private String provider;

	@Column(name = "PAYMENT_SOURCE")
	private String paymentSource;

	@Column(name = "PAYMENT_TYPE")
	private String paymentType;

	@Column(name = "PLATFORM")
	private String platform;

	@Column(name = "PREAUTH_METHOD", length = 5)
	private String preauthMethod;

	@Column(name = "PRODUCT_CODE", length = 25)
	private String productCode;

	@Column(name = "PRODUCT_DESC")
	private String productDesc;

	@Column(name = "PROFILE_ID")
	private String profileId;

	@Column(name = "QR_DATA_ID")
	private String qrDataId;

	@Column(name = "REFERENCE_ID")
	private String referenceId;

	@Column(name = "REFERENCE_NUMBER")
	private String referenceNumber;

	@Lob
	@Column(name = "REMARK")
	private String remark;

	@Column(name = "SALES_DESCRIPTION")
	private String salesDescription;

	@Column(name = "SUP_WALLET_ID", length = 20)
	private String supWalletId;

	@Column(name = "THIRD_PARTY_REF_NUM")
	private String thirdPartyRefNum;

	@Column(name = "TID")
	private String tid;

	@Column(name = "TOPUP_METHOD")
	private String topupMethod;

	@Column(name = "TOPUP_MODE")
	private String topupMode;

	@Column(name = "TOPUP_PAYMENT_ID")
	private String topupPaymentId;

	@Column(name = "TOPUP_TRUSTEE_PROCESS")
	private Boolean topupTrusteeProcess;

	@Column(name = "TRACE_NO", length = 100)
	private String traceNo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "TRANSACTION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	@Column(name = "TRANSACTION_ID", unique = true)
	@NotNull(message = "transactionId must not be null")
	private String transactionId;

	@Column(name = "TRANSACTION_MODE")
	private String transactionMode;

	@Column(name = "TRANSACTION_STATUS")
	@NotNull(message = "transaction must not empty")
	@NotEmpty(message = "transaction status empty") // statusId
	private String transactionStatus;

	@Column(name = "TRANSACTION_TYPE")
	@NotNull(message = "transactionType must not empty")
	@NotEmpty(message = "transactionType code")
	private String transactionType;

	@Column(name = "TRX_CURRENCY_CODE")
	@NotNull(message = "trxCurrencyCode must not empty")
	@NotEmpty(message = "empty currency code") // currency
	private String trxCurrencyCode;

	@Column(name = "TRX_SEQUENCE_COUNTER")
	private String trxSequenceCounter;

	@Column(name = "TRX_SOURCE")
	private String trxSource;

	@Column(name = "TRX_UNIQUE_KEY")
	private String trxUniqueKey;

	@Column(name = "UDID")
	private String udid;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "VOIDED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date voidedDate;

	@Column(name = "WALLET_CARD_NO")
	private String walletCardNo;

	@Column(name = "WALLET_ID")
	private String walletId;

	@Column(name = "WALLET_USER_NAME")
	private String walletUserName;

	@Column(name = "BATCH_FK", length = 20)
	private BigInteger batchFk;

	@Column(name = "CARD_TOKEN_ID", length = 20)
	private BigInteger cardTokenId;

	@Column(name = "INPUT_TYPE")
	private String inputType;

//	@Column(name = "APPROVAL_FK", length = 20)
//	private BigInteger approvalFk;

	@OneToOne(mappedBy = "transaction")
	private CMSMerchantTransactionApproval approval;

	@Column(name = "CROSS_CHECK_STATUS")
	private String crossCheckStatus;

	@Column(name = "REFUND_REASON", columnDefinition = "TEXT")
	private String refundReason;

	@Column(name = "FEE_TYPE")
	private String feeType;

	@Column(name = "SERVICE_NAME")
	private String serviceName;

	@Column(name = "PROCESSING_FEE_AMT")
	private BigInteger processingFeeAmt;

	@Column(name = "PROCESSING_FEE_PERCENT")
	private BigDecimal processingFeePercent;

	@Column(name = "MIN_PROCESSING_FEE_AMT")
	private BigInteger minProcessingFeeAmt;

	@Column(name = "REFUND_PROCESSING_FEE_AMT")
	private BigInteger refundProcessingFeeAmt;

	@Column(name = "PRODUCT_DETAIL_STATUS")
	private String productDetailStatus;

	@Column(name = "MIN_TRANS_AMT")
	private BigInteger minTransAmt;

	@Column(name = "PROVIDER_CODE")
	private String providerCode;

	@Column(name = "PROVIDER_SERVICE")
	private String providerService;

	@Column(name = "PROVIDER_FEE_FIXED_AMOUNT")
	private BigInteger providerFeeFixedAmount;

	@Column(name = "PROVIDER_FEE_PERCENT")
	private BigInteger providerFeePercent;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "CROSS_CHECK_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date crossCheckDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "SETTLED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date settledDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "CREDIT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creditDate;

	@Column(name = "RECV_ACCT_ID")
	private String recvAcctId;

	@Column(name = "RECV_ACCT_NAME")
	private String recvAcctName;

	@Column(name = "RECV_NAPAS_BIN")
	private String recvNapasBin;

	@Column(name = "RECV_VAL_POOL")
	private String recvValPool;

	@Column(columnDefinition = "LONGTEXT", name = "BACKEND_URL")
	private String backendUrl;

}
