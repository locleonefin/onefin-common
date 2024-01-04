package com.onefin.ewallet.common.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.billpay.base.BillpayPartnerProvider;
import com.onefin.ewallet.common.domain.billpay.imedia.IMediaBillPayTransaction;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "APPROVAL_TASK_HISTORY")
public class CMSMerchantTransactionApprovalHistory {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)", name = "ID")
	private UUID id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = true, updatable = false, name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "VERSION", nullable = false, length = 11)
	private int version;


	@Column(name = "CHECKER_ID")
	private String checkerId;

	@Column(name = "CHECKER_TYPE")
	private String checkerType;

	@Column(name = "REASON", columnDefinition = "TEXT")
	private String reason;

	@Column(name = "STATUS")
	@NotNull(message = "status is empty")
	private String status;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPROVAL_FK")
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private CMSMerchantTransactionApproval cmsMerchantTransactionApproval;
}
