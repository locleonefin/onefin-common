package com.onefin.ewallet.common.domain.bank.vietin;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(
		indexes = {
				@Index(name = "trans_time_date_index", columnList = "transTimeDate")
		},
		name = "VietinNotifyTrans"
)
@Data
@EqualsAndHashCode(callSuper = true)
public class VietinNotifyTransTable extends VietinNotifyTrans {
	@Column(length = 8)
	private String transTimeDate;

	public void setTransTime(String s) {
		super.setTransTime(s);
		this.setTransTimeDate(s.substring(0, 8));
	}

	@Override
	public String toString() {
		return "VietinNotifyTransTable(" +
				"transTimeDate='" + transTimeDate + '\'' + " " +
				"VietinNotifyTrans=" + super.toString() + "}";
	}
}
