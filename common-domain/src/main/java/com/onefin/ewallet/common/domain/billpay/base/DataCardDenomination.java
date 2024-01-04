package com.onefin.ewallet.common.domain.billpay.base;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "datacard_denom_denomCode_index", columnList = "denomCode,partner")
})
public class DataCardDenomination extends AbstractBillPay {

	@Column(length = 10)
	private String denomCode;		// VT50 | MB100 | ...
	
	// For end user
	@Column
	private BigDecimal denomination;	// mệnh giá miêm yết trên ứng dụng cho end user
	
	@Column
	private BigDecimal discountAmount;	// số tiền được giảm giá - cho end user
	
	@Column
	private BigDecimal debitAmount;		// số tiền phải thanh toán - của end user

	@Column
	private BigDecimal cashback;		// số tiền được hoàn lại vào ví cho end user
	
	@Column(length = 20)
	private String partner;			// code of partner	IOMEDIA | Other
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private MobileCardProvider mobileCardProvider;
	
	public BigDecimal getCashback() {
		if (this.cashback == null) {
			this.cashback = new BigDecimal(0);
		}
		return this.cashback;
	}
	public BigDecimal getDiscountAmount() {
		if (this.discountAmount == null) {
			this.discountAmount = new BigDecimal(0);
		}
		return this.discountAmount;
	}
	public BigDecimal getDebitAmount() {
		if (this.debitAmount == null) {
			this.debitAmount = new BigDecimal(0);
		}
		return this.debitAmount;
	}
	public BigDecimal getDenomination() {
		if (this.denomination == null) {
			this.denomination = new BigDecimal(0);
		}
		return this.denomination;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataCardDenomination that = (DataCardDenomination) o;

        return (this.id.equals(that.id));
    }
	
	@Override
    public int hashCode() {
        int result = this.id.hashCode();
        return 31 * result;
    }
	
	public DataCardDenomination(String[] params) {
		if (params == null || params.length == 0) {
			return;
		}
		if (params.length >= 1) {
			this.denomCode = params[0];
		}
		if (params.length >= 2) {
			try {
				this.denomination = new BigDecimal(params[1]);
			} catch (Exception e) {}
		}
		
		this.discountAmount = new BigDecimal(0);
		if (params.length >= 3) {
			try {
				this.discountAmount = new BigDecimal(params[2]);
			} catch (Exception e) {}
		}
		
		this.debitAmount = new BigDecimal(0);
		if (params.length >= 4) {
			try {
				this.debitAmount = new BigDecimal(params[3]);
			} catch (Exception e) {}
		}
		
		this.cashback = new BigDecimal(0);
		/*if (params.length >= 5) {
			try {
				this.cashback = new BigDecimal(params[4]);
			} catch (Exception e) {}
		}*/
		
		if (params.length >= 7) {
			this.partner = params[6];
		}
	}

}
