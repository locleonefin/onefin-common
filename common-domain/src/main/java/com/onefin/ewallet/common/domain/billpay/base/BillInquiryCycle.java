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
@Table(indexes = {
		@Index(name = "inquiry_cycle_billId_index", columnList = "billId")
})
public class BillInquiryCycle extends AbstractBillPay implements Comparable<BillInquiryCycle> {
	
	@Column
	private String billId;		// VietinBank billId

	@Column
	private String ofId;			// OneFin generated ID
	
	@Column(length = 20)
	private String fromDate;		// if only return billCycle, assign to fromDate
	
	@Column(length = 20)
	private String toDate;
		
	private BigDecimal billAmount;
	
	@Column(length = 255)
	private String note;

	@Column
	private String description;		//For ASC only

	@Column
	private String serviceCode;     //For ASC only

	@Column
	private boolean selected = true;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_inquiry_detail_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private BillInquiryDetail billInquiryDetail;

	@Override
	public int compareTo(BillInquiryCycle d) {
		if (ofId == null && (d == null || d.getOfId() == null)) {
			return 0;
		}
		if (ofId != null && d != null && d.getOfId() != null) {
			return this.ofId.compareTo(d.getOfId());
		}
		return -1;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillInquiryCycle that = (BillInquiryCycle) o;

        return (this.id.equals(that.id));
    }
	
	@Override
    public int hashCode() {
        int result = this.id.hashCode();
        return 31 * result;
    }

}
