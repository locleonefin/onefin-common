package com.onefin.ewallet.common.base.errorhandler;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
 * @author thaita
 */
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExceptionHandler.class);

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		LOGGER.error("Async Exception: ", ex);
		LOGGER.error("Exception message - " + ex.getMessage());
		LOGGER.error("Method name - " + method.getName());
		for (Object param : params) {
			LOGGER.error("Parameter value - " + param);
		}
	}
}