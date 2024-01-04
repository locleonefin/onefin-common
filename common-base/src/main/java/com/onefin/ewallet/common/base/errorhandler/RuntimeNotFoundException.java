package com.onefin.ewallet.common.base.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onefin.ewallet.common.base.constants.OneFinConstants;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = OneFinConstants.CONN_NOT_FOUND)
public class RuntimeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RuntimeNotFoundException() {
		super(OneFinConstants.CONN_NOT_FOUND);
	}

	public RuntimeNotFoundException(String cause) {
		super(cause);
	}
}
