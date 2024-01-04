package com.onefin.ewallet.common.domain.billpay.iomedia;

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
DROP TABLE IF EXISTS iomedia_billpay_transaction_view;
CREATE OR REPLACE VIEW iomedia_billpay_transaction_view AS SELECT 
		a.*
FROM 
  ((SELECT 
	bt.id as id,
	bt.created_date as created_date,
	bt.service_code as service_code,
	bt.provider_code as provider_code,
	bt.provider_name as provider_name,
	bt.product_code as product_code,
	bt.quantity as quantity,
	bt.paid_amount as paid_amount,
	bt.amount as amount,
	bt.trans_type as trans_type, 
	bt.tran_status as trans_status,
	IFNULL(bt.billing_code, bt.phone) as phone,
	bt.wallet_id as wallet_id
  FROM iomedia_billpay_transaction bt WHERE bt.tran_status IN ('SUCCESS','PENDING') AND bt.trans_type IN ('BC','TU'))
  UNION ALL
  (SELECT 
	vnp.id as id,
	vnp.created_date as created_date,
	vnp.service_code as service_code,
	vnp.provider_code as provider_code,
	vnp.provider_name as provider_name,
	vnp.product_code as product_code,
	0 as quantity,
	vnp.amount as paid_amount,
	vnp.amount as amount,
	'TU' as trans_type, 
	vnp.tran_status as trans_status,
	vnp.mobile_no as phone,
	vnp.wallet_id as wallet_id
  FROM vnpay_topup_transaction vnp WHERE vnp.tran_status IN ('SUCCESS','PENDING'))) a ORDER BY a.created_date DESC;
  
 * 
 * @author khanh
 *
 */
@Data
@Entity
@Table(name = "iomedia_billpay_transaction_view")
public class IomediaBillPayTransactionView {

	@Id
	private UUID id;

	@JsonIgnore
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column
	private String serviceCode;		// buyCard | directTopup | payBill | sendSMS
	
	@Column
	private String providerCode;

	@Column
	private String providerName;

	@JsonIgnore
	@Column
	private String productCode;			// telcoCode

	@Column
	private int quantity;

	@Column
	private BigDecimal paidAmount;		// topupAmount   : số tiền KH đã thanh toán cho giao dịch, yêu cầu phải hợp lệ với số tiền nợ cước trên hàm query
	
	@JsonIgnore
	@Column
	private BigDecimal amount;			// Số tiền hóa đơn đã được thanh toán thành công
	
	@JsonIgnore
	@Column
	private String transType;

	@Column
	private String transStatus;
	
	@Column
	private String phone;

	@JsonIgnore
	@Column
	private String walletId;
	
	@JsonProperty
	public String getCreatedDate() {
		if (this.createdDate != null) {
			return (new SimpleDateFormat(DomainConstants.DATE_FORMAT_TRANS)).format(this.createdDate);
		}
		return "";
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IomediaBillPayTransaction that = (IomediaBillPayTransaction) o;

        return (this.getId().equals(that.getId()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getId().hashCode();
        return 37 * result;
    }
}
