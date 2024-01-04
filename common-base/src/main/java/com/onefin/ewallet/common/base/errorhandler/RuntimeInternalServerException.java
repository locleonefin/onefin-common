package com.onefin.ewallet.common.base.errorhandler;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onefin.ewallet.common.base.constants.OneFinConstants;

import java.util.List;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = OneFinConstants.CONN_INTERNAL_SERVER_ERROR)
public class RuntimeInternalServerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private List<Object> listArgumentsVariable;

	public RuntimeInternalServerException() {
		super(OneFinConstants.CONN_INTERNAL_SERVER_ERROR);
	}

	public RuntimeInternalServerException(String cause) {
		super(cause);
	}

	public Object[] getListArgumentsVariable(){
		if(listArgumentsVariable == null || listArgumentsVariable.isEmpty()) return null;

		Object[] array = listArgumentsVariable.toArray(new Object[listArgumentsVariable.size()]);
		return array;
	}

	public RuntimeInternalServerException setListArgumentsVariable(List<Object> listArgumentsVariable){
		this.listArgumentsVariable = listArgumentsVariable;
		return this;
	}

}