package com.onefin.ewallet.common.base.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	@Around("execution(* com.onefin.ewallet..VietinNotiController..*(..))")
	public Object logVietinNotiController(ProceedingJoinPoint joinPoint) throws Throwable {
		Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());

		// Before
		String sb = "== START " +
				joinPoint.getSignature().getName();
		logger.info(sb);

		// execute method
		Object result = joinPoint.proceed();

		// After
		logger.info("== END {} ", joinPoint.getSignature().getName());

		return result;
	}

	@Around("execution(* com.onefin.ewallet..controller..*(..))")
	public Object logMethodParameters(ProceedingJoinPoint joinPoint) throws Throwable {
		Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());

		// Before
		StringBuilder sb = new StringBuilder();
		sb.append("== START ");
		sb.append(joinPoint.getSignature().getName());
		sb.append(": ");
		for (Object arg : joinPoint.getArgs()) {
			sb.append(arg);
			sb.append(", ");
		}
		logger.info(sb.toString());

		// execute method
		Object result = joinPoint.proceed();

		// After
		logger.info("== END {} ", joinPoint.getSignature().getName());

		return result;
	}

	@Around("execution(* com.onefin.utility..controller..*(..))")
	public Object logUtilsServiceMethodParameters(ProceedingJoinPoint joinPoint) throws Throwable {
		Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());

		// Before
		StringBuilder sb = new StringBuilder();
		sb.append("== START ");
		sb.append(joinPoint.getSignature().getName());
		sb.append(": ");
		for (Object arg : joinPoint.getArgs()) {
			sb.append(arg);
			sb.append(", ");
		}
		logger.info(sb.toString());

		// execute method
		Object result = joinPoint.proceed();

		// After
		logger.info("== END {} ", joinPoint.getSignature().getName());

		return result;
	}

	@Around("execution(* com.onefin.merchant..controller..*(..))")
	public Object logMerchantUtilsServiceMethodParameters(ProceedingJoinPoint joinPoint) throws Throwable {
		Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());

		// Before
		StringBuilder sb = new StringBuilder();
		sb.append("== START ");
		sb.append(joinPoint.getSignature().getName());
		sb.append(": ");
		for (Object arg : joinPoint.getArgs()) {
			sb.append(arg);
			sb.append(", ");
		}
		logger.info(sb.toString());

		// execute method
		Object result = joinPoint.proceed();

		// After
		logger.info("== END {} ", joinPoint.getSignature().getName());

		return result;
	}

	@Around("execution(* com.onefin.transit.controller.portal..*(..))")
	public Object logMethodTransitPortalParameters(ProceedingJoinPoint joinPoint) throws Throwable {
		Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        int maxLogLength = 1000;

		// Before
		StringBuilder sb = new StringBuilder();
		sb.append("== START ");
		sb.append(joinPoint.getSignature().getName());
		sb.append(": ");
		for (Object arg : joinPoint.getArgs()) {
			sb.append(arg);
			sb.append(", ");
		}

        if (sb.length() > maxLogLength) {
          logger.info(String.format("%s....", sb.substring(0, maxLogLength)));
        }
        else {
          logger.info(sb.toString());
        }

		// execute method
		Object result = joinPoint.proceed();

		// After
		logger.info("== END {} ", joinPoint.getSignature().getName());

		return result;
	}

}