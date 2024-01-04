package com.onefin.ewallet.common.domain.merchant;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;

import lombok.Data;

@Data
@Entity
@Table(name = "pg_merchant_transactions_vw")
public class PgMerchantTransactionsVw {

	@Id
	@Column(name = "merchant_transaction_id")
	private String merchantTrxId;

	@Column(name = "primary_mid")
	private String primaryMid;

	@Column(name = "merchant_code")
	private String merchantCode;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "trx_amount")
	private BigDecimal trxAmount;

	@Column(name = "amount_charges")
	private BigDecimal amountCharges;

	public BigDecimal getTrxAmount() {
		if (trxAmount != null) {
			return trxAmount.divide(new BigDecimal(100));
		}
		return null;
	}


	public BigDecimal getAmountCharges() {
		if (amountCharges != null) {
			return amountCharges.divide(new BigDecimal(100));
		}
		return null;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "trx_date")
	private Date trxDate;

	@Column(name = "trx_id")
	private String trxId;

	@Column(name = "trx_unique_key")
	private String trxUniqueKey;

	@Column(name = "trans_status")
	private String transStatus;

	@Column(name = "aproval_code")
	private String approvalCode;

	@Column(name = "trx_type")
	private String trxType;

	@Column(name = "channel_type")
	private String channelType;

	@Column(name = "issuer_name")
	private String issuerName;

	@Column(name = "issuer_trx_id")
	private String issuerTrxId;

	@Column(name = "id")
	private Long id;

	@Column(name = "acquier_trx_id")
	private String acquirerTrxId;

	@Column(name = "tid")
	private String tid;

	@Column(name = "acquirer_name")
	private String acquirerName;

	@Column(name = "card_no")
	private String cardNo;

	@Column(name = "wallet_id")
	private String walletId;

	@Column(name = "wallet_user_name")
	private String walletUserName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "reference_number")
	private String referenceNumber;

	@Column(name = "remark")
	private String remark;

	@Column(name = "THIRD_PARTY_REF_NUM")
	private String thirdPartyRefNum;

	@Column(name = "payment_source")
	private String paymentSource;

	@Column(name = "business_reg_no")
	private String businessRegNo;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "bank_account")
	private String bankAccount;

	@Column(name = "booking_no")
	private String bookingNo;

	@Column(name = "bank_code")
	private String bankCode;

}
