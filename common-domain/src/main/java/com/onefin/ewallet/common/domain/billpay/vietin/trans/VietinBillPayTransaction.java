package com.onefin.ewallet.common.domain.billpay.vietin.trans;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;

import lombok.Data;

@Data
@Entity
@Table(name = "Vietin_Billpay_Transaction")
public class VietinBillPayTransaction extends AbstractBaseEwalletTrans {
	
	@Column
	private String walletId;

	@Column
	private String merchantId;

	@Column
	private String serviceCode;

	@Column
	private String onefinProviderCode;

	@Column
	private String svrProviderCode;		// this is providerCode

	@Column
	private String providerName;

	@Column
	private String serviceType;

	@Column
	private String custId;

	@Column
	private BigDecimal amount;

	@Column
	private String currencyCode;

	@Column
	private String billCycle;

	@Column
	private String billId;

	@Column
	private String billName;

	@Column
	private String billAddress;

	@Column
	private String ofBillId;

	@Column
	private String transTime;

	@Column
	private String transMessage;

	@Column
	private String bankTransId;
		
	@Column
	private String email;
	
	@Column
	private String phone;
	
	@Column
	private String language;

	@Column
	private String additionalData;

	@Column
	private String lastModifiedBy;			// add new for last updated user.
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VietinBillPayTransaction that = (VietinBillPayTransaction) o;

        return (this.getId().equals(that.getId()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getId().hashCode();
        return 39 * result;
    }
}
