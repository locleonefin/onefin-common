package com.onefin.ewallet.common.domain.napas;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

import lombok.Data;

@Data
@Entity
@Table(name = "Napas_Ewallet_Ew_Transaction_View")
public class NapasEwalletEWTransactionView extends AbstractBaseEwalletTrans {

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
	
	@Column(length = 255)
	private String subMerCode;

	@Column(length = 1)
	private String env;

}
