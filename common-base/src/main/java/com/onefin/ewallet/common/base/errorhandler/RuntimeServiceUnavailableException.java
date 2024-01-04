package com.onefin.ewallet.common.base.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onefin.ewallet.common.base.constants.OneFinConstants;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = OneFinConstants.CONN_PARTNER_TIMEOUT_RESPONSE)
public class RuntimeServiceUnavailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RuntimeServiceUnavailableException() {
		super(OneFinConstants.CONN_PARTNER_TIMEOUT_RESPONSE);
	}

	public RuntimeServiceUnavailableException(String cause) {
		super(cause);
	}
}