package com.onefin.ewallet.common.domain.settlement;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Entity
@Data
@Immutable
@Table(name = "bill_report_view")
public class VietinBillSummary {
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="date_range")
	private String dateRange;
	
	@Column(name="transaction_count")
	private String transactionCount;
	
	@Column(name="transaction_sum")
	private BigDecimal transactionSum;
	
	@Column(name="svr_provider_code")
	private String svrProviderCode;
	
	@Column(name="service_type")
	private String serviceType;
	
}
