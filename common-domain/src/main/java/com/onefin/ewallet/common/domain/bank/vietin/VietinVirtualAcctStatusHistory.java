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
@Table(name = "Vietin_Virtual_Acct_Status_History")
public class VietinVirtualAcctStatusHistory {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Column
	@Size(max = 13)
	private String requestId;

	@Column
	private String virtualAcctCode;

	@Column
	private String virtualAcctVar;

	@Column
	@Size(max = 10)
	private String newStatus;

	@Column
	@Size(max = 10)
	private String statusCode;

	@Column(name = "fixed_amount")
	private BigDecimal fixedAmount;

	@Column(name = "virtual_acct_name")
	private String virtualAcctName = "";

	@Column(name = "bank_code")
	private String bankCode = "";

	@Column(name = "bank_time")
	private Long bankTime;

	@Column(name = "msg_id")
	private String msgId = "";

	@Column(name = "request_error_message")
	private String requestErrorMessage = "";

	@Column(name = "data_error_message")
	private String dataErrorMessage = "";

	@Column(name = "client_user_id")
	private String clientUserId = "";

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
}
