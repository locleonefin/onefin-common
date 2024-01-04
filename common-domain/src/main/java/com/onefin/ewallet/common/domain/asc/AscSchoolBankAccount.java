package com.onefin.ewallet.common.domain.asc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.bank.common.BankListDetails;
import com.onefin.ewallet.common.domain.base.StringListConverter;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Asc_School_Bank_Account")
public class AscSchoolBankAccount {

	@Id
	@Column(unique = true, nullable = false)
	protected String accId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column
	private String accountNo;

	@Column
	private String accountHolderName;

//	@Column
//	private String branch;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private BankListDetails bankDetails;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AscSchool school;

	@Column
	private String createdUser;

	@Column
	private Boolean napas247;

	@Column
	private Boolean accountStatus;

	@Override
	public int hashCode() {
		int result = this.accId.hashCode();
		return result;
	}

}
