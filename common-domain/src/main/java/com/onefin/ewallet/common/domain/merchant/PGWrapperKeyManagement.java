package com.onefin.ewallet.common.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import com.onefin.ewallet.common.domain.merchant.PGPaymentMethod;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "pg_wrapper_key_management")
public class PGWrapperKeyManagement {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	@JsonIgnore
	private UUID id;

	@Column(nullable = false, unique = true)
	private String merchantCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date updatedDate;

	@Column(nullable = false, length = 11)
	@JsonIgnore
	private int version;

	@ToString.Exclude
	@Lob
	@Column
	private String merchantPublicKey;

	@ToString.Exclude
	@Lob
	@Column
	private String merchantPrivateKey;


	@ToString.Exclude
	@Lob
	@Column
	@JsonIgnore
	private String merOfPublicKey;

	@ToString.Exclude
	@Lob
	@Column
	private String merOfPrivateKey;

	@ToString.Exclude
	@Lob
	@Column
	@JsonIgnore
	private String ofPgPublicKey;

	@ToString.Exclude
	@Lob
	@Column
	private String ofPgPrivateKey;

	@ToString.Exclude
	@Lob
	@Column
	private String pgPublicKey;

	@ToString.Exclude
	@Lob
	@Column
	private String pgPrivateKey;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "pgWrapperKeyManagement")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PGPaymentMethod> paymentMethods;

	@Column
	private int fraudMaxAttempt;

	@Column
	private Integer paymentCycle;

}
