package com.onefin.ewallet.common.domain.billpay.vnpay.trans;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "Vnpay_Topup_Transaction")
public class VnpayTopupTransaction extends AbstractBaseEwalletTrans {
	
	@Column
	private String connResult; // Using phase 2

	@Column
	private String mobileNo;

	@Column
	private BigDecimal requestAmount;

	@Column
	private BigDecimal amount;

	@Column
	private long trace;

	@Column
	private String localDateTime;

	@Column
	private String partnerCode;

	@Column
	private String respCode;

	@Column
	private String respMessage;

	@Column
	private BigDecimal balance;

	@Column
	private String vnPayDateTime;

	@Column
	private String walletId;

	@Column
	private String serviceCode;
	
	@Column
	private String providerCode;

	@Column
	private String productCode;			// telcoCode

	@Column
	private String providerName;

}
