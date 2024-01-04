package com.onefin.ewallet.common.domain.billpay.base;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onefin.ewallet.common.domain.constants.DomainConstants;

import lombok.Data;

/**
 * Query for this view:
 * 
  DROP TABLE IF EXISTS billpay_transaction_history_view;
  CREATE OR REPLACE VIEW billpay_transaction_history_view AS SELECT 
		a.*
  FROM 
	((SELECT
		vt.id as id,
		vt.cust_id as bill_no,
		vt.bill_cycle as bill_cycle,
		vt.bill_name as bill_name,
		vt.amount as amount,
		vt.service_code as service_code,
		vt.onefin_provider_code as provider_code,
		vt.provider_name as provider_name,
		vt.wallet_id as wallet_id,
		vt.tran_status as trans_status,
		vt.created_date as created_date
	FROM vietin_billpay_transaction vt WHERE vt.api_operation = 'PAYBILL' AND vt.tran_status IN ('SUCCESS','PENDING'))
	UNION ALL
	(SELECT
		iom.id as id,
		iom.billing_code as bill_no,
		iom.bill_cycle as bill_cycle,
		iom.bill_name as bill_name,
		iom.amount as amount,
		iom.service_code as service_code,
		iom.provider_code as provider_code,
		iom.provider_name as provider_name,
		iom.wallet_id as wallet_id,
		iom.tran_status as trans_status,
		iom.created_date as created_date
	FROM iomedia_billpay_transaction iom WHERE iom.api_operation = 'PAYBILL' AND iom.trans_type = 'VB' AND iom.tran_status IN ('SUCCESS','PENDING'))) a ORDER BY a.created_date DESC;

 * 
 * 
 * @author khanh
 *
 */
@Data
@Entity
@Table(name = "billpay_transaction_history_view")
public class BillpayTransactionHistoryView {

	@Id
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Column
	private String billNo;

	@Column
	private String billCycle;
	
	@Column
	private String billName;

	@Column
	private BigDecimal amount;

	@Column
	private String serviceCode;

	@Column
	private String providerCode;

	@Column
	private String providerName;
	
	@Column
	@JsonIgnore
	private String walletId;
	
	@Column
	private String transStatus;

	@JsonIgnore
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@JsonProperty
	public String getCreatedDate() {
		return (new SimpleDateFormat(DomainConstants.DATE_FORMAT_TRANS)).format(this.createdDate);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillpayTransactionHistoryView that = (BillpayTransactionHistoryView) o;

        return (this.getId().equals(that.getId()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getId().hashCode();
        return 39 * result;
    }
}
