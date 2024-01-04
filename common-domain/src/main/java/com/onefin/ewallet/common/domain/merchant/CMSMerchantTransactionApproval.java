package com.onefin.ewallet.common.domain.merchant;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.bank.transfer.BankTransferChildRecords;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "APPROVAL")
public class CMSMerchantTransactionApproval {

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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name = "APPROVED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

	@Column(name = "APPROVED_BY")
	private String approvedBy;

	@Column(name = "VERSION", nullable = false, length = 11)
	private int version;

	@Column(name = "ACTION")
	private String action;

	@Column(name = "REF_AMOUNT1")
	private BigInteger refAmount1;

	@Column(name = "REF_NO1")
	private String refNo1;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "TRANSACTION_ID")
	private String transactionId;

	@Column(name = "CMS_REQUEST_ID")
	private String cmsRequestId;

//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "TRANSACTION_FK", nullable = false)
//	@ToString.Exclude
//	private CMSMerchantTransaction transaction;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSACTION_FK",
			referencedColumnName = "id", nullable = false)
	@ToString.Exclude
	private CMSMerchantTransaction transaction;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cmsMerchantTransactionApproval")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<CMSMerchantTransactionApprovalHistory> approvals;

	@Lob
	@Column(name = "MC_FILE_DATA")
	private String mcFileData;

	@Lob
	@Column(name = "PROVIDER_FILE_DATA")
	private String providerFileData;

	public void addApprovalHistory(CMSMerchantTransactionApprovalHistory approval) {
		if (approvals == null) {
			approvals = new HashSet<>();
		}
		approvals.add(approval);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}
}
