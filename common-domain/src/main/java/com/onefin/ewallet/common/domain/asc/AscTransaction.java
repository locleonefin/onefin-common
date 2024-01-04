package com.onefin.ewallet.common.domain.asc;

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
@Table(name = "Asc_Transaction")
public class AscTransaction {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Column
	private String requestId; // From OneFin or partner

	@Column
	private String tranStatus; // PENDING, SUCCESS, ERROR

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(length = 50)
	private String itemCode;

	@Column
	private String itemCodeId;

	@Column
	private String itemName;

	@Column
	private BigDecimal price; // adready convert to cent (x100)

	@Column
	private int quantity;

	@Column
	private BigDecimal itemAmount;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "asc_school_bank_account_acc_id")
	private AscSchoolBankAccount bankAccount;

	@Transient
	private String merchantCode;

	@Transient
	private String trxRefNo;

	@Column
	private String associateMerchantCode;

	@Column
	private String settleRefId;

	@Column
	private String schoolBankAccount;
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(referencedColumnName = "id")
//	private BankTransferTransaction settleTrans;

}
