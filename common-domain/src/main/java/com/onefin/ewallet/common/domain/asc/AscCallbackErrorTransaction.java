package com.onefin.ewallet.common.domain.asc;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Asc_Callback_Error_Transaction")
public class AscCallbackErrorTransaction {

	@Id
	@GenericGenerator(name = DomainConstants.UUID, strategy = DomainConstants.UUID2)
	@GeneratedValue(generator = DomainConstants.UUID)
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column
	private String requestId;

	@Column
	private String merchantCode;

	@Column
	private int retry;

	@Column
	private Boolean processed; // default 0
	@Column
	private String updatedUser;

	public AscCallbackErrorTransaction(Date createdDate, Date updatedDate, String requestId, String merchantCode, int retry, Boolean processed) {
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.requestId = requestId;
		this.merchantCode = merchantCode;
		this.retry = retry;
		this.processed = processed;
	}
}

