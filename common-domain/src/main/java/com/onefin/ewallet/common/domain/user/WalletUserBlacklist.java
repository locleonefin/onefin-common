package com.onefin.ewallet.common.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.base.StringListConverter;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Wallet_User_Blacklist")
public class WalletUserBlacklist {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column
	@CsvBindByName(column = "fullName")
	private String fullName;

	@Column
	@CsvBindByName(column = "aliasNames")
	private String aliasNames;

	@Column
	@CsvBindByName(column = "dob")
	private String dob;

	@Column
	@CsvBindByName(column = "nationality")
	private String nationality;

	@Column
	@CsvBindByName(column = "idNo")
	private String idNo;

	@Column
	@CsvBindByName(column = "hometown")
	private String hometown;

	@Column
	@CsvBindByName(column = "address")
	private String address;

	@Column
	@CsvBindByName(column = "occupation")
	private String occupation;

	@Column
	@CsvBindByName(column = "ethnic")
	private String ethnic;

	@Column
	@CsvBindByName(column = "religion")
	private String religion;

}
