package com.onefin.ewallet.common.base.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onefin.ewallet.common.base.constants.OneFinConstants;

@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason = OneFinConstants.CONN_UNSUPPORTED_MEDIA_TYPE)
public class RuntimeUnsupportedMediaTypeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RuntimeUnsupportedMediaTypeException() {
		super(OneFinConstants.CONN_UNSUPPORTED_MEDIA_TYPE);
	}

	public RuntimeUnsupportedMediaTypeException(String cause) {
		super(cause);
	}
}