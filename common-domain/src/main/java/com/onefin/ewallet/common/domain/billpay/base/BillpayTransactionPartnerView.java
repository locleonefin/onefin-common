package com.onefin.ewallet.common.domain.billpay.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Query for this view:
 * 
DROP TABLE IF EXISTS billpay_transaction_partner_view;
CREATE OR REPLACE VIEW billpay_transaction_partner_view AS SELECT 
		a.*
FROM 
	((SELECT vt.id as id, 
			vt.request_id as request_id, 
			vt.tran_status as res_code, 
			vt.trans_message as res_message,
			'VIETINBANK' as partner,
			'paybill' as trans_type
	FROM vietin_billpay_transaction vt WHERE vt.request_id IS NOT NULL)
	UNION ALL
	(SELECT iom.id as id, 
			iom.request_id as request_id, 
			iom.res_code as res_code, 
			iom.res_message as res_message,
			'IOMEDIA' as partner,
			iom.trans_type as trans_type
	FROM iomedia_billpay_transaction iom  WHERE iom.request_id IS NOT NULL AND iom.trans_type IN ('VB','BC','TU'))
	UNION ALL
	(SELECT vpt.id as id, 
			vpt.request_id as request_id, 
			vpt.resp_code as res_code, 
			vpt.resp_message as res_message,
			'VNPAY' as partner,
			'TU' as trans_type
	FROM vnpay_topup_transaction vpt  WHERE vpt.request_id IS NOT NULL)) a;
 * 
 * 
 * 
 * @author khanh
 *
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "Billpay_Transaction_Partner_View")
public class BillpayTransactionPartnerView {
	
	@Id
	@Column(unique = true, nullable = false, columnDefinition = "BINARY(16)")
	protected UUID id;

	@Column(length = 20)
	private String requestId;		// transactionId from SoftSpace / OneFin
	
	@Column
	private String resCode;

	@Column
	private String resMessage;
	
	@Column(length = 20)
	private String partner;
	
	@Column(length = 20)
	private String transType;

	@Column(length = 100)
	private String productCode;

	@Column(length = 100)
	private String originalTransId;
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillpayTransactionPartnerView that = (BillpayTransactionPartnerView) o;

        return (this.id.equals(that.id));
    }
	
	@Override
    public int hashCode() {
        int result = this.id.hashCode();
        return 31 * result;
    }

}
