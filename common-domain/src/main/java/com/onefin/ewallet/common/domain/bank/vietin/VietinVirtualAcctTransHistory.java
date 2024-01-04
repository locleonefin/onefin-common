package com.onefin.ewallet.common.domain.bank.vietin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Vietin_Virtual_Acct_Trans_History")
public class VietinVirtualAcctTransHistory {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column
	private String tranStatus; // PENDING, SUCCESS, ERROR

	@Column
	private String virtualAcctVar;

	@Column
	private String virtualAcctId;

	@Column
	private String transUniqueKey;

	@Column
	private String merchantCode;

//	@Column
//	private String partner;

	@Column
	private BigDecimal amount;

	@Column(columnDefinition = "TEXT")
	private String backendUrl;

	@Column(columnDefinition = "TEXT")
	private String qrUrl;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireTime;

	@Column
	private UUID vietinNotiUuid;

	@Column
	private Boolean customerInquiry;

	@Column(columnDefinition = "TEXT")
	private String remark;

	@Column
	private String bankCode;
}
