package com.onefin.ewallet.common.domain.merchant;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "MERCHANT_REQUEST_INFO")
public class CMSMerchantTransactionRequestInfo {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)", name = "ID")
	private UUID id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = true, updatable = false, name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "MERCHANT_CODE")
	@NotNull(message = "merchant code must not be null")
	@NotEmpty(message = "Empty merchant code") // merchantCode
	private String merchantCode;

	@Column(name = "PRIMARY_MID")
	private String primaryMid;

	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "TRX_UNIQUE_KEY")
	private String trxUniqueKey;

	@Column(name = "TRANSACTION_ID", unique = true)
	@NotNull(message = "transactionId must not be null")
	private String transactionId;

}
