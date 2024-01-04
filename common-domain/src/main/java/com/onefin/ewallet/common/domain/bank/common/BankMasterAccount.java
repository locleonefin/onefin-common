package com.onefin.ewallet.common.domain.bank.common;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;

import lombok.Data;

@Data
//@Entity
//@Table(name = "Bank_Master_Account")
public class BankMasterAccount {

	@Id
	private String accountId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bank_code", referencedColumnName = "code")
	private BankList bank;

	@Column
	@NotEmpty(message = "Not empty")
	private String holderName;

	@Column
	@NotEmpty(message = "Not empty")
	private String placeIssue;

	@Column
	private String currentBalance;

	@Column
	private String currencyCode;
	
	@NotEmpty(message = "Not empty")
	private String createdBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

}
