package com.onefin.ewallet.common.domain.napas;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

import lombok.Data;

@Data
@Entity
@Table(name = "Napas_Ewallet_Ce_Transaction")
public class NapasEwalletCETransaction extends AbstractBaseEwalletTrans {

	@Column
	private String napasResult;

	@Column
	private String connResult;

	@Column
	private String orderId;

	@Column
	private String cardNumber;

	@Column
	private BigDecimal amount;

	@Column
	private String merchantId;

	@Column
	private String transactionDate;

	@Column
	private String acquirerTransId;

	@Column
	private String token;

	@Column
	private boolean validIpn;

	@Column
	private String ewalletResult;

	@Column
	private String errorCode;

	@Column(columnDefinition = "TEXT")
	private String message;

	@Column
	private String bankCode;

	@Column
	private String cardIssueDate;

	@Column
	private String cardName;

	@Column
	private String senderAccountNumber;

	@Column
	private String senderAccountName;

	@Column
	private String operationId;

	@Column
	private String napasResultId;

	@Column
	private String napasResultCode;

	@Column
	private BigDecimal settlementAmount;

	@Column
	private String settlementDate;

}
