package com.onefin.ewallet.common.domain.bank.vietin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "vietin_efast_transaction_vw")
public class VietinEfastTransactionView {

	private static final Logger LOGGER = LoggerFactory.getLogger(VietinEfastTransactionView.class);


	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID onefinId;

	@Column
	private BigDecimal onefinAmount;

	@Column
	private String onefinBackendUrl;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column
	private Date onefinCreatedDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column
	private Date onefinExpireTime;

	@Column
	private String onefinMerchantCode;

	@Column
	private String onefinQrUrl;

	@Column
	private String onefinTranStatus;

	@Column
	private String onefinTransUniqueKey;

	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	private Date onefinUpdatedDate;

	@Column
	private UUID onefinVietinNotiUuid;

	@Column
	private String onefinVirtualAcctId;

	@Column
	private String onefinVirtualAcctVar;


	@Column(columnDefinition = "BINARY(16)")
	private UUID efastVietinNotiUuid;

	@Column
	private String efastAmount;

//	@Column
//	private String efastBalance;

	@Column
	private String efastBankTransId;

	@Column
	private String efastBillId;

	@Column
	private String efastBillTerm;

	@Column
	private String efastChannelId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column
	private Date efastCreatedDate;

	@Column
	private String efastCurrencyCode;

	@Column
	private String efastCustAcct;

	@Column
	private String efastCustCode;

	@Column
	private String efastCustName;

	@Column
	private String efastEmail;

	@Column
	private String efastFee;

	@Column
	private String efastIdCard;

	@Column
	private String efastOriginalId;

	@Column
	private String efastPayRefInfo;

	@Column
	private String efastPayRefNo;

	@Column
	private String efastPaymentMethod;

	@Column
	private String efastPaymentType;

	@Column
	private String efastPhoneNo;

	@Column
	private String efastPriority;

	@Column
	private String efastRecordNo;

	@Column
	private String efastRecvAcctId;

	@Column
	private String efastRecvAcctName;

	@Column
	private String efastRecvAddr;

	@Column
	private String efastRecvBankId;

	@Column
	private String efastRecvBranchId;

	@Column
	private String efastRecvCity;

	@Column
	private String efastRecvCountry;

	@Column
	private String efastRecvVirtualAcctId;

	@Column
	private String efastRecvVirtualAcctName;

	@Column
	private String efastRemark;

	@Column
	private String efastSendAcctId;

	@Column
	private String efastSendAcctName;

	@Column
	private String efastSendAddr;

	@Column
	private String efastSendBankId;

	@Column
	private String efastSendBranchId;

	@Column
	private String efastSendCity;

	@Column
	private String efastSendCountry;

	@Column
	private String efastSendVirtualAcctId;

	@Column
	private String efastSendVirtualAcctName;

	@Column
	private String efastServiceType;

	@Column
	private String efastStatusCode;

	@Column
	private String efastStatusMessage;

	@Column
	private String efastTransId;

	@Column
	@JsonIgnore
	private String efastTransTime;

	@JsonProperty
	public String getEfastTransTime() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DomainConstants.DATE_FORMAT_TRANS);
			return formatter.format(DateTime.parse(efastTransTime, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toLocalDateTime().toDate());
		} catch (Exception e) {
			return "";
		}
	}

	@Column
	private String efastTransType;

	@Column
	private String efastVat;

	@JsonIgnore
	@Column
	private String efastTransTimeDate;

	@Column
	private String bankCode;

	@JsonProperty
	public String getEfastTransTimeDate() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DomainConstants.DATE_FORMAT_TRANS3);
			return formatter.format(DateTime.parse(efastTransTimeDate, DateTimeFormat.forPattern("yyyyMMdd")).toLocalDateTime().toDate());
		} catch (Exception e) {
			return "";
		}
	}

}
