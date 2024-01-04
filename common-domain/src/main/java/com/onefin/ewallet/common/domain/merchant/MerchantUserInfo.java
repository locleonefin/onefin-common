package com.onefin.ewallet.common.domain.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "merchant_user_info")
public class MerchantUserInfo {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Column
	private String requestId; // From OneFin or partner

	@Column
	private String merchantCode; // From OneFin or partner

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column
	private String mobileNo;

	@Column
	private String email;

	@Column
	private String addressLine1;

	@Column
	private String addressLine2;

	@Column
	private String addressLine3;

	@Column
	private String city;

	@Column
	private String postcode;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String memberId;
}
