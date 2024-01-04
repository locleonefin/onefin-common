package com.onefin.ewallet.common.domain.bank.vietin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Vietin_Virtual_Acct")
public class VietinVirtualAcctTable {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Column
	@Size(max = 2)
	private String language;

	@Column
	private String virtualAcctCode;

	@Column(unique = true, nullable = false)
	private String virtualAcctVar;

	@Column
	private String maxCredit;

	@Column
	private String minCredit;

	@Column
	private String creditExpireDate;

	@Column
	private String debitExpireDate;

	@Column
	private String maxDebit;

	@Column
	private String minDebit;

	@Column
	private String effectiveDate;

	@Column
	private String expireDate;

	@Column
	private String virtualAcctName;

	@Column
	private String virtualAcctStatus;

	@Column
	private String productCode;

	@Column
	private String productName;

	@Column
	private String customerCode;

	@Column
	private String customerName;

	@Column
	private String virtualAcctId;

	@Column
	private String qrURL;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column
	private boolean inUse;

	@Column
	private String poolName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseTime;

	@Column(name = "partner_code")
	private String partnerCode = "";

	@Column(name = "fixed_amount")
	private BigDecimal fixedAmount;

	@Column(name = "acc_type")
	private String accType = "";

	@Column(name = "bank_time")
	private Long bankTime;

	@Column(name = "partner_acc_no")
	private String partnerAccNo = "";

	@Column(name = "maker_id")
	private String makerId = "";

	@Column(name = "acc_name")
	private String accName = "";

	@Column(name = "checker_id")
	private String checkerId = "";

	@Column(name = "msg_id")
	private String msgId = "";

	@Column(name = "ccy")
	private String ccy = "";

	@Column(name = "bank_code")
	private String bankCode = "";

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "re_open_date", columnDefinition = "TIMESTAMP", updatable = true)
	private Date reOpenDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_date", columnDefinition = "TIMESTAMP", updatable = true)
	private Date closeDate;

}
