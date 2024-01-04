package com.onefin.ewallet.common.domain.settlement;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.base.StringListConverter;
import com.onefin.ewallet.common.domain.constants.DomainConstants;

import lombok.Data;

@Data
@Entity
@Table(name = "Settlement_Transaction")
public class SettlementTransaction {

	@EmbeddedId
	private SettleKey settleKey;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> file;

	@Column
	private String status; // PENDING ERROR SUCCESS

}
