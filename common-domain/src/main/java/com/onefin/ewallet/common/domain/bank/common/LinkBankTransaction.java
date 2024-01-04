package com.onefin.ewallet.common.domain.bank.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "Link_Bank_Transaction")
public class LinkBankTransaction extends AbstractBaseEwalletTrans {

	@Column(length = 10)
	private String linkType;

	@Column(length = 3)
	private String currency;

	@Column
	private BigDecimal amount;

	@Column
	private String tokenId;

	@Column
	private String tokenNumber;

	@Column(length = 2)
	private String tokenExpireMonth;

	@Column(length = 4)
	private String tokenExpireYear;

	@Column
	private String tokenIcvv;

	@Column(length = 20)
	private String tokenState;

	@Column(length = 10)
	private String otpCode;

	@Column
	private boolean sendOtp;

	@Column(length = 100)
	private String walletId;

	@Column
	private String merchantId;

	@Column
	private String merchantRefId;

	@Column
	private String holderName;

	@Column(length = 20)
	private String cardAccountNumber;

	@Column
	private String cardUserId;

	@Column
	private String cardInsId;

	@Column
	private String cardAuthorizationId;

	@Column(length = 6)
	private String cardIssueDate;

	@Column
	private String bankStatusCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date transDate;

	@Column
	private String refundId;

	@Column
	private String vcbRequestId;

	@Column
	private String bankRequestTrans;

	@Column
	private Boolean requestFromBank;

	@Column(length = 35)
	private String bankTransactionId;

	@Column(length = 15)
	private String phoneNum;

	@Column
	private String ssRequestId;

	@Column
	private String bank;

	@Transient
	private String connResult;



}
