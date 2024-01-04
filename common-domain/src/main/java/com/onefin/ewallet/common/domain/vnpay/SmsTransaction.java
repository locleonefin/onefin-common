package com.onefin.ewallet.common.domain.vnpay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

import lombok.Data;

@Data
@Entity
@Table(name = "Sms_Transaction")
public class SmsTransaction extends AbstractBaseEwalletTrans {

	@Column
	private String partnerRequestId;

	@Column
	private String partner;

	@Column
	private String phone;

	@Column
	private String sender;

//	@Column
//	private String keyword;

	@Column
	private String shortMessage;

	@Column
	private String otpCode;

	@Column
	private String referenceCode;

	@Column
	private String encryptMessage;

	@Column
	private int isEncrypt;

	@Column
	private int type;

	@Column
	private Long requestTime;

//	@Column
//	private String partnerCode;

	@Column
	private String status;

	@Column
	private String description;

	@Column
	private String providerId;

	@Column
	private String providerIdOrg;

	@Column
	private Integer mnp;

	@Column(length = 2)
	private String lang; // en, vi
}
