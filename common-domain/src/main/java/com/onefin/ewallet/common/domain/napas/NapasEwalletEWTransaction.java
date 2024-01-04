package com.onefin.ewallet.common.domain.napas;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

import lombok.Data;

@Data
@Entity
@Table(name = "Napas_Ewallet_Ew_Transaction")
public class NapasEwalletEWTransaction extends AbstractBaseEwalletTrans {

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

	@Column(length = 20)
	private String subMerCode;

	@Column(length = 1)
	private String env;

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
	private String referenceId;

	@Column
	private String source;

}
