package com.onefin.ewallet.common.base.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onefin.ewallet.common.base.constants.OneFinConstants;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = OneFinConstants.CONN_UNSUPPORTED_OPERATION_EXCEPTION)
public class RuntimeUnsupportedOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RuntimeUnsupportedOperationException() {
		super(OneFinConstants.CONN_UNSUPPORTED_OPERATION_EXCEPTION);
	}

	public RuntimeUnsupportedOperationException(String cause) {
		super(cause);
	}
}
