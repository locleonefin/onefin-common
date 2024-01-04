package com.onefin.ewallet.common.domain.bank.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.bank.common.BankList;
import com.onefin.ewallet.common.domain.bank.common.BankListDetails;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "Bank_Transfer_Child_Records")
public class BankTransferChildRecords {

	@Id
	@Column(length = 25)
	private String transId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(length = 25)
	private int priority;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "code")
	private BankList senderBank;

	@Column(length = 100)
	private String senderAcctId;

	@Column(length = 100)
	private String senderAcctName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "code")
	private BankList recvBank;

	@Column(length = 100)
	private String recvAcctId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "id")
	private BankListDetails recvBranchId;

	@Column(length = 100)
	private String recvAcctName;

	@Column
	private BigDecimal amount;

	@Column(length = 50)
	private String payRefNo;

	@Column(length = 100)
	private String payRefInfor;

	@Column(length = 100)
	private String remark;

	@Column(length = 10)
	private String bankStatusCode;

	@Column
	private BigDecimal feeAmount;

	@Column
	private BigDecimal vatAmount;

	@Column(length = 100)
	private String bankTransactionId;

	@Column(length = 10)
	private String currency;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BankTransferTransaction bankTransferTransaction;

	@Column(columnDefinition = "TEXT")
	private String message;

	@Column(length = 100, name = "recv_acct_card")
	private String recvAcctCard;

	@Column(name = "settle_date")
	private String settleDate;

	@Column(name = "vccb_onus")
	private String vccbOnus;

	@Column(name = "vccb_error_code")
	private String vccbErrorCode;

	@Override
	public int hashCode() {
		int result = this.transId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return transId;
	}

}
