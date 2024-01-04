package com.onefin.ewallet.common.base.errorhandler;


import com.onefin.ewallet.common.base.constants.OneFinConstants.CommonConnectorCode;
import com.onefin.ewallet.common.base.model.BaseConnResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


@RestControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<?> handleException(HttpServletRequest request, Exception e) throws Exception {
		try {
			BaseConnResponse responseMessage = new BaseConnResponse();
			String localizedMessage = getRootException(e).getLocalizedMessage();
			CommonConnectorCode connCode = CommonConnectorCode.stream().filter(t ->
					t.getCode().equals(localizedMessage)
			).findFirst().orElse(null);

			// Default error code
			if (connCode == null) {
				responseMessage.setConnectorCode(CommonConnectorCode.E_CONN_INTERNAL_SERVER_ERROR.getCode());
				responseMessage.setMessage(localizedMessage != null ? localizedMessage : CommonConnectorCode.E_CONN_INTERNAL_SERVER_ERROR.getMessage());
			} else {
				responseMessage.setConnectorCode(connCode.getCode());
				responseMessage.setMessage(connCode.getMessage());
			}
			LOGGER.error(String.format("Exception handlers: %s", responseMessage.getMessage()), e);
			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		} catch (Exception ex) {
			throw ex;
		}
	}

	@ExceptionHandler(value = RuntimeInternalServerException.class)
	public ResponseEntity<?> handleException(HttpServletRequest request, RuntimeInternalServerException e, Locale locale) throws Exception {
		try {
			String message = messageSource.getMessage(e.getMessage(), e.getListArgumentsVariable(), locale);
			return handleException(request, new RuntimeException(message));
		} catch (Exception exception) {
			return handleException(request, e);
		}
	}

	@ExceptionHandler(value = RuntimeBadRequestException.class)
	public ResponseEntity<?> handleException(HttpServletRequest request, RuntimeBadRequestException e, Locale locale) throws Exception {
		try{
			String message = messageSource.getMessage(e.getMessage(), e.getListArgumentsVariable(), locale);
			return handleException(request, new RuntimeException(message));
		}catch (Exception exception){
			return handleException(request, e);
		}
	}

	public static Throwable getRootException(Throwable exception) {
		Throwable rootException = exception;
		while (rootException.getCause() != null) {
			rootException = rootException.getCause();
		}
		return rootException;
	}
}