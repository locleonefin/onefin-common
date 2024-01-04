package com.onefin.ewallet.common.domain.bank.vietin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public class VietinNotifyTrans {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Column
	private String msgType;

	@Column
	private String transId;
	@Column
	private String originalId;
	@Column
	private String channelId;
	@Column
	private String priority;
	@Column
	private String recordNo;
	@Column
	private String transTime;
	@Column
	private String transType;
	@Column
	private String serviceType;
	@Column
	private String paymentType;
	@Column
	private String paymentMethod;
	@Column
	private String custCode;
	@Column
	private String custName;
	@Column
	private String custAcct;
	@Column
	private String idCard;
	@Column
	private String phoneNo;
	@Column
	private String email;
	@Column
	private String sendBankId;
	@Column
	private String sendBranchId;
	@Column
	private String sendAcctId;
	@Column
	private String sendAcctName;
	@Column
	private String sendVirtualAcctId;
	@Column
	private String sendVirtualAcctName;
	@Column
	private String sendAddr;
	@Column
	private String sendCity;
	@Column
	private String sendCountry;
	@Column
	private String recvBankId;
	@Column
	private String recvBranchId;
	@Column
	private String recvAcctId;
	@Column
	private String recvAcctName;
	@Column
	private String recvVirtualAcctId;
	@Column
	private String recvVirtualAcctName;
	@Column
	private String recvAddr;
	@Column
	private String recvCity;
	@Column
	private String recvCountry;
	@Column
	private String billId;
	@Column
	private String billTerm;
	@Column
	private String amount;
	@Column
	private String fee;
	@Column
	private String vat;
	@Column
	private String balance;
	@Column
	private String payRefNo;
	@Column
	private String payRefInfo;
	@Column
	private String bankTransId;
	@Column(columnDefinition = "TEXT")
	private String remark;
	@Column
	private String statusCode;
	@Column
	private String statusMessage;
	@Column
	private String currencyCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column
	@JsonProperty("bank_code")
	private String bankCode = "";

	@Column
	@JsonProperty("from_bank_name")
	private String fromBankName = "";

	@Column
	@JsonProperty("ac_entry_sr_no")
	private String acEntrySrNo = "";

	@Column
	@JsonProperty("bank_time")
	private Long bankTime;

	@Column
	@JsonProperty("event_name")
	private String eventName = "";

	@Column
	@JsonProperty("trace_id")
	private String traceId = "";

	@Column
	@JsonProperty("source_trans")
	private String sourceTrans = "";

	@Column
	@JsonProperty("trn_ref_no")
	private String trnRefNo = "";

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("txn_init_dt")
	private Date txnInitDt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("value_dt")
	private Date valueDt;

	@Column
	@JsonProperty("partner_code")
	private String partnerCode = "";

	@Column
	@JsonProperty("partner_id")
	private String partnerId = "";

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("transaction_date")
	private Date transactionDate;

	@Column
	@JsonProperty("credit_account")
	private String creditAccount = "";

	@Column
	@JsonProperty("cr_acc")
	private String crAcc = "";

	@Column
	@JsonProperty("napas_trace_id")
	private String napasTraceId = "";


}
