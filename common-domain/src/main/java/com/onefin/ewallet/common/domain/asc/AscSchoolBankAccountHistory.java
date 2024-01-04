package com.onefin.ewallet.common.domain.asc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.bank.common.BankListDetails;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Asc_School_Bank_Account_History")
public class AscSchoolBankAccountHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	protected String accId;

	@Column
	private String accountHolderName;

	@Column
	private String accountNo;

//	@Column
//	private String branch;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private BankListDetails bankDetails;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column
	private String modifyBy;

	@Column
	private int version;

	@Override
	public int hashCode() {
		int result = this.accId.hashCode();
		return result;
	}

}
