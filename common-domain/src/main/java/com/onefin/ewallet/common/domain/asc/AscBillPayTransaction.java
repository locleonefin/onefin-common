package com.onefin.ewallet.common.domain.asc;

import com.onefin.ewallet.common.domain.base.AbstractBaseEwalletTrans;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Asc_Billpay_Transaction")
public class AscBillPayTransaction extends AbstractBaseEwalletTrans {
	
	@Column
	private String walletId;

	@Column
	private String requestId;

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
	private String providerCode;

	@Column
	private String serviceType;

	@Column
	private String custId;

	@Column
	private BigDecimal amount;

	@Column
	private String currencyCode;

	@Column
	private String billInquiryDetailId;

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
	private String errorCode;

	@Column
	private String transPaymentMessage;

	@Column
	private String paymentErrorCode;

	@Column
	private String ascTransId;
		
	@Column
	private String email;
	
	@Column
	private String phone;
	
	@Column
	private String language;

	@Column
	private String additionalData;

	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AscBillPayTransaction that = (AscBillPayTransaction) o;

        return (this.getId().equals(that.getId()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getId().hashCode();
        return 31 * result;
    }
}
