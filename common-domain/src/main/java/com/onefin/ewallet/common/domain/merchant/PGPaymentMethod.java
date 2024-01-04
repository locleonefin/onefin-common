package com.onefin.ewallet.common.domain.merchant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "pg_payment_method")
public class PGPaymentMethod {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	@JsonIgnore
	private UUID id;

	@Column
	private int paymentMethod;

	@Column
	private boolean active;

	@Transient
	private String paymentMethod_string;

	@Transient
	private String minTrxAmounts_string;

	@ElementCollection(fetch = FetchType.EAGER, targetClass = MinTrxAmountCurrency.class)
	@CollectionTable(name = "pg_payment_method_min_transaction_amounts")
	private List<MinTrxAmountCurrency> minTrxAmounts;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private PGWrapperKeyManagement pgWrapperKeyManagement;

	@Column
	private boolean visible;

	@Column
	private String virAcctPool;

	@Column
	private String qrLogo;

	@Column
	private String bankCode;

}
