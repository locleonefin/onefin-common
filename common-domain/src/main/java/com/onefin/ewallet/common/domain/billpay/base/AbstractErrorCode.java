package com.onefin.ewallet.common.domain.billpay.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractErrorCode {

	@Id
	@Column(unique = true, nullable = false)
	protected String code;

	@Column
	protected String messageVi;

	@Column
	protected String messageEn;

}
