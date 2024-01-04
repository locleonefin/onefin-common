package com.onefin.ewallet.common.domain.billpay.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(indexes = {
		@Index(name = "inquiry_detail_billNo_index", columnList = "billNo"),
		@Index(name = "inquiry_detail_requestId_index", columnList = "id,walletId,billNo"),
		@Index(name = "inquiry_detail_walletId_index", columnList = "walletId")
})
public class BillInquiryDetail extends AbstractBillPay {
	
	@Column(length = 20)
	private String requestId;		// transactionId from SoftSpace / OneFin
	
	
	/*******  Partner info  ******/
	@Column(length = 20)
	private String providerId;		// used for Vietin Only
	
	@Column(length = 20)
	private String merchantId;		// partnerCode (Iomedia)
	
	
	/*******  Service, Provider info  ******/
	@Column(length = 30)
	private String onefinProviderCode;

	@Column(length = 30)
	private String partnerProviderCode;

	@Column(length = 50)
	private String providerCodeExt;		// used for Vietin Only

	@Column(length = 100)
	private String providerName;
	
	@Column(length = 50)
	private String serviceCode;
	
	@Column(length = 20)
	private String serviceType;		// Used for Vietin only
	
	@Column(length = 20)
	private String partner;
	
	
	/*******  User info  ******/	
	@Column(length = 20)
	private String walletId;
	
	@Column(length = 12)
	private String phone;
	
	@Column(length = 50)
	private String email;
	
	
	/*******  Bill Detail info  ******/
	@Column
	private String billId; 			// IDKhoaChinh (ASC only)
	@Column(length = 30)
	private String billNo;			// custId (VT) | billingCode (Iomedia)
	
	@Column(length = 250)
	private String billName;
	
	@Column(length = 250)
	private String billAddress;
		
	@Column
	private BigDecimal billAmount;
	
	@Column
	private BigDecimal minAmount;

	@Column(length = 3)
	private String currency;

	@Column(length = 2)
	private String language;
	
	@Column
	private Integer payIndex;

	/*******  Bill Cycle info  ******/
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "billInquiryDetail")
	private Set<BillInquiryCycle> billCycles;
	

	public Set<BillInquiryCycle> getBillCycles() {
		if (this.billCycles == null) {
			this.billCycles = new HashSet<>();
		}
		return this.billCycles;
	}
	
	public void setBillCycles(Set<BillInquiryCycle> billCycles) {
		this.getBillCycles().clear();
		this.getBillCycles().addAll(billCycles);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillInquiryDetail that = (BillInquiryDetail) o;

        return (this.id.equals(that.id));
    }
	
	@Override
    public int hashCode() {
        int result = this.id.hashCode();
        return 31 * result;
    }

}
