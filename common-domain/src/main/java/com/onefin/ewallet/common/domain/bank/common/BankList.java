package com.onefin.ewallet.common.domain.bank.common;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.base.StringListConverter;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "Bank_List")
public class BankList implements Serializable {

	@Id
	private String code; // citad

	@NotEmpty(message = "Not empty")
	private String name;

	@Column
	private String englishName;

	@Column
	private String ssCode;

	@Column(length = 10)
	private String bankCode;

	@Column
	private Boolean headquarters;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(length = 20, name = "napas_bin")
	@Convert(converter = StringListConverter.class)
	private List<String> napasBin;

	@Column(name = "napas_bin", insertable = false, updatable = false)
	private String pgBin; //

	@Column
	private Boolean allowEwallet;

	@Column
	private Boolean allowPg;

	@ToString.Exclude
	@JsonBackReference
	@OneToMany(mappedBy = "bankList", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<BankListDetails> detail; // Use for bank transfer

	@Transient
	private Set<BankListDetails> detailView;

	@Column
	private String modifiedBy;

	@Column(name = "vccb_bank_id")
	private String vccbBankId;


	@Column(length = 20, name = "vccb_ben_id")
	@Convert(converter = StringListConverter.class)
	private List<String> vccbBenId;


	@Column(length = 20, name = "vccb_bin_id")
	@Convert(converter = StringListConverter.class)
	private List<String> vccbBinId;

	@Column(name = "is_account_transfer")
	private Boolean isAccountTransfer;

	@Column(name = "is_card_transfer")
	private Boolean isCardTransfer;

	@Column(name = "is_account_recv")
	private Boolean isAccountRecv;

	@Column(name = "is_card_recv")
	private Boolean isCardRecv;

	@Column(name = "is_custom_citad")
	private Boolean isCustomCitad;


}
