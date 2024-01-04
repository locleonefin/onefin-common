package com.onefin.ewallet.common.domain.billpay.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Query for this view:
 * 
	DROP TABLE IF EXISTS bill_inquiry_detail_view;
	CREATE OR REPLACE VIEW bill_inquiry_detail_view AS select * from bill_inquiry_detail WHERE DATE(created_date) = CURDATE();
 * 
 * 
 * @author khanh
 *
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Bill_Inquiry_Detail_View")
public class BillInquiryDetailView {
	
	@Id
	protected UUID id;

	@Column
	private String billId;

	@Column(length = 20)
	private String requestId;		// transactionId from SoftSpace / OneFin
	
	
	/*******  Partner info  ******/
	@Column(length = 20)
	private String providerId;		// used for Vietin Only
	
	@Column(length = 20)
	private String merchantId;		// partnerCode (Iomedia)
	
	
	/*******  Service, Provider info  ******/
	@Column(length = 20)
	private String onefinProviderCode;

	@Column(length = 20)
	private String partnerProviderCode;

	@Column(length = 20)
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
	private List<BillInquiryCycle> billCycles;

	/*******  Bring from main table  ******/
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updatedDate;

	public List<BillInquiryCycle> getBillCycles() {
		if (this.billCycles == null) {
			this.billCycles = new ArrayList<>();
		}
		return this.billCycles;
	}
	
	public void setBillCycles(List<BillInquiryCycle> billCycles) {
		this.getBillCycles().clear();
		this.getBillCycles().addAll(billCycles);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillInquiryDetailView that = (BillInquiryDetailView) o;

        return (this.id.equals(that.id));
    }
	
	@Override
    public int hashCode() {
        int result = this.id.hashCode();
        return 31 * result;
    }

}
