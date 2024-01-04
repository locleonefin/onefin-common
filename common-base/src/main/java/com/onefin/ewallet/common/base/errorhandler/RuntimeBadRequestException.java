package com.onefin.ewallet.common.base.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onefin.ewallet.common.base.constants.OneFinConstants;

import java.util.List;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = OneFinConstants.CONN_BAD_REQUEST)
public class RuntimeBadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private List<Object> listArgumentsVariable;

	public RuntimeBadRequestException() {
		super(OneFinConstants.CONN_BAD_REQUEST);
	}

	public RuntimeBadRequestException(String cause) {
		super(cause);
	}

	public Object[] getListArgumentsVariable(){
		if(listArgumentsVariable == null || listArgumentsVariable.isEmpty()) return null;

		Object[] array = listArgumentsVariable.toArray(new Object[listArgumentsVariable.size()]);
		return array;
	}

	public RuntimeBadRequestException setListArgumentsVariable(List<Object> listArgumentsVariable){
		this.listArgumentsVariable = listArgumentsVariable;
		return this;
	}
}
