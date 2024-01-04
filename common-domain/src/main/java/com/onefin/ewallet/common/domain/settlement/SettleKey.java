package com.onefin.ewallet.common.domain.settlement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Embeddable
public class SettleKey implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	private String settleDate; // yyyy-MM-dd,yyyy-MM

	@NotNull
	@Column
	private String partner;

	@NotNull
	@Column
	private String domain;

	public SettleKey() {
	}

	public SettleKey(String settleDate, String partner, String domain) {
		this.settleDate = settleDate;
		this.partner = partner;
		this.domain = domain;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SettleKey that = (SettleKey) o;

		if (!settleDate.equals(that.settleDate) || !partner.equals(that.partner) || !domain.equals(that.domain))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = settleDate.hashCode();
		result = 31 * result + partner.hashCode() + domain.hashCode();
		return result;
	}

}
