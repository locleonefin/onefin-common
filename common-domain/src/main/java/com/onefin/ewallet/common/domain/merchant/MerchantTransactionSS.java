package com.onefin.ewallet.common.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "softspace_transactions")
public class MerchantTransactionSS {

//	@Id
//	@Column
//	private String id;

	@Id
	@Column
	private String trxId;

	@Column
	private String schoolCode;

	@Column
	private String ssTrxId;

	@Column
	private String referenceNumber;

	@Column
	private String thirdPartyRefNum;

//	@Column
//	private String businessName;

	@Column
	private String primaryMid;

	@Column
	private String transStatus;

	@Column
	private String aprovalCode;

	@Column
	private String issuerName;

	@Column
	private String issuerTrxId;

	@Column
	private String acquierTrxId;

	@Column
	private String tid;

	@Column
	private String acquirer_Name;

	@Column
	private String latitude;

	@Column
	private String longitude;

	@Column
	private String altitude;

//	@Column
//	private String businessBankName;

//	@Column
//	private String businessBankAccount;

	@Column
	private String cardNo;

	@Column
	private String walletUserName;

	@Column
	private BigDecimal trxAmount;

//	@Column
//	private String businessLicense;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column
	private Date trxDate;

	@Column
	private String trxType;

	@Column
	private String channelType;

	@Transient
	private String cardType;

	@Transient
	private String paymentMethod;

	@PostLoad
	void fillTransient() {
		if (channelType.equals("SPENDING_ECOMMERCE_QR_DYNAMIC")) {
			paymentMethod = "VDT";
		} else if (channelType.equals("SPENDING_CHANNEL_TYPE_LOCAL_DEBIT")){
			paymentMethod = "CTT";
			cardType = "ATM";
		} else if (channelType.equals("SPENDING_CHANNEL_TYPE_THIRD_PARTY_BANK_PAYMENT")) {
			paymentMethod = "CTT";
			cardType = "VIETQR";
		} else if (channelType.equals("SPENDING_CHANNEL_TYPE_CREDIT_DEBIT_CARD")
				|| channelType.equals("SPENDING_CHANNEL_TYPE_CREDIT_DEBIT_CARD_INT")) {
			paymentMethod = "CTT";
			cardType = "Visa/Master/JCB";
		} else {
			paymentMethod = "";
			cardType = "";
		}
	}

	@Column
	private String partner;

	@Column
	private String trxUniqueKey;

	@Column
	private String shopName;

	@Column
	private String walletId;

	@Column
	private BigDecimal amountCharges;

	@Column
	private String merchantCode;

	@Column
	private String mobileNo;

	@Column
	private String email;

	@Column
	private String addressLine1;

	@Column
	private String addressLine2;

	@Column
	private String addressLine3;

	@Column
	private String city;

	@Column
	private String postcode;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String fullName;

	@Column
	private String memberId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column
	private Date modifiedDate;

	@Column
	private String remark;

	@Column
	private String unitLevel;

	@Column
	private String modifiedBy;

}
