package com.onefin.ewallet.common.domain.notification.common;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class KeyValueModel {

	private String code;

	private String value;

}
