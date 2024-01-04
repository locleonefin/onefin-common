package com.onefin.ewallet.common.domain.merchant;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@Embeddable
public class MinTrxAmountCurrency {

	@Column
	private BigDecimal amount;

	@Column(nullable = false)
	private String currency;

}
