package com.onefin.ewallet.common.domain.errorCode;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
@Embeddable
public class PartnerErrorCodeKey implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@CsvBindByName(column = "partner")
	private String partner;

	@NotNull
	@Column
	@CsvBindByName(column = "domain")
	private String domain;

	@NotNull
	@Column
	@CsvBindByName(column = "code")
	private String code;

	@Transient
	@CsvBindByName(column = "baseCode")
	private String baseCode;

	public PartnerErrorCodeKey() {
	}

	public PartnerErrorCodeKey(String partner, String domain, String code) {
		this.partner = partner;
		this.domain = domain;
		this.code = code;
	}

}
