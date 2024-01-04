package com.onefin.ewallet.common.domain.bank.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "Bank_List_Details")
public class BankListDetails implements Serializable {

	@Id
	private int id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(length = 50)
	private String province;

	@Column(length = 100)
	private String branchName;

	@Column(length = 50)
	private String branchCode;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIgnore
	private BankList bankList;

	@Column
	private String modifiedBy = "";

	@Override
	public int hashCode() {
		int result = this.id;
		return result;
	}

}
