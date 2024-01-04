package com.onefin.ewallet.common.domain.billpay.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "Billpay_Partner_Provider")
public class BillpayPartnerProvider extends AbstractBillPay {

	@Column
	private String providerCode;

	@Column
	private String partner;

	@JsonIgnore
	@Column
	private boolean active;
	
	@Column
	private String serviceType;			// Used for VietinBank services only.

	@JsonIgnore
	@Column
	private String billPrefix;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BillpayOnefinProvider billpayOnefinProvider;

	public BillpayPartnerProvider(String[] params) {
		if (params == null || params.length == 0) {
			return;
		}

		if (params.length >= 2) {
			this.setProviderCode(params[1]);
		}
		if (params.length >= 3) {
			this.setPartner(params[2]);
		}
		if (params.length >= 4) {
			this.setActive(false);
			try {
				this.setActive(Boolean.parseBoolean(params[3]));
			} catch (Exception e) {}
		}
		if (params.length >= 5) {
			this.setServiceType(params[4]);
		}
		if (params.length >= 6) {
			this.setBillPrefix(params[5]);
		}
	}
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillpayPartnerProvider that = (BillpayPartnerProvider) o;

        return (this.getId().equals(that.getId()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getId().hashCode();
        return 31 * result;
    }
}
