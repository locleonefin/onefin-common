package com.onefin.ewallet.common.base.errorhandler;

import com.onefin.ewallet.common.base.constants.OneFinConstants;
import com.onefin.ewallet.common.base.model.BaseConnResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	// error handle for @Valid
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatus status, WebRequest request) {
		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		BaseConnResponse responseMessage = new BaseConnResponse();
		try {
			responseMessage.setMessage(errors.stream()
					.map(n -> messageSource.getMessage(n,null,LocaleContextHolder.getLocale()))
					.collect(Collectors.joining("-", "[", "]")));
		}catch (Exception e){
			responseMessage.setMessage(errors.stream()
					.map(n ->  String.valueOf(n))
					.collect(Collectors.joining("-", "[", "]")));
		}

		LOGGER.error("== Connector validation fail requestbody: {} -- {} -- {}",
				((ServletWebRequest) request).getRequest().getRequestURI(), errors, ex.getBindingResult());
		responseMessage.setConnectorCode(OneFinConstants.CONN_BAD_REQUEST);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

}
