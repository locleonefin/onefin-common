package com.onefin.ewallet.common.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseConnResponse implements Serializable {

	private String connectorCode;

	private String message;

}
