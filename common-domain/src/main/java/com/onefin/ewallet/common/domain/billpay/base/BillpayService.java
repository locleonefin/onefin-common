package com.onefin.ewallet.common.domain.billpay.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Billpay_Service")
public class BillpayService extends AbstractBillPayServiceProvider {

	@Column(length = 30)
	private String name;

	@JsonIgnore
	@Column(length = 10)
	private String menu;

	@JsonIgnore
	@Column
	private boolean active;
	
	@JsonIgnore
	@Column(length = 50)
	private String napasMerchantId;
	
	@JsonIgnore
	@Column(length = 50)
	private String napasSubMerchantCode;

	public BillpayService(String[] params) {
		if (params == null || params.length == 0) {
			return;
		}

		if (params.length >= 1) {
			this.setCode(params[0]);
		}
		if (params.length >= 2) {
			this.setName(params[1]);
		}
		if (params.length >= 3) {
			this.setMenu(params[2]);
		}
		if (params.length >= 4) {
			this.setActive(false);
			try {
				this.setActive(Boolean.parseBoolean(params[3]));
			} catch (Exception e) {}
		}
		if (params.length >= 5) {
			this.setNapasMerchantId(params[4]);
		}
		if (params.length >= 6) {
			this.setNapasSubMerchantCode(params[5]);
		}
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillpayService that = (BillpayService) o;

        return (this.getCode().equals(that.getCode()));
    }
	
	@Override
    public int hashCode() {
        int result = this.getCode().hashCode();
        return 31 * result;
    }

}
