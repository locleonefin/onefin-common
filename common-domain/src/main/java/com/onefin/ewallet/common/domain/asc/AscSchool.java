package com.onefin.ewallet.common.domain.asc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.base.StringListConverter;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "Asc_School")
public class AscSchool {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(unique = true, nullable = false)
	private String code;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column
	private String unitLevel;

	@Column
	private String province;

	@Column
	private String district;

	@Column
	private String name;

	@Column
	private String address;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> email;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> ccEmail;

	@Column
	private Boolean sendMail;

	@ToString.Exclude
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<AscSchoolBankAccount> bankAccounts;

	@Column
	private String associateMid; // Primary MID


	@Override
	public int hashCode() {
		int result = this.id.hashCode();
		return 31 * result;
	}
}
