package com.onefin.ewallet.common.domain.settlement;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onefin.ewallet.common.domain.constants.DomainConstants;

import lombok.Data;

@Entity
@Data
@Table(name = "bill_report_view")
public class VietinBillReport {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DomainConstants.DATE_FORMAT_TRANS, timezone = DomainConstants.HO_CHI_MINH_TIME_ZONE)
	@Column(name="created_date",nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	
	@Id
	@Column(name="request_id")
	private String requestId;
	
	@Column(name="amount")
	private BigDecimal amount;
	
	@Column(name="svr_provider_code")
	private String svrProviderCode;
	
	@Column(name="service_type")
	private String serviceType;

	@Column(name="CHANNEL")
	private String channel;
	
	
}
