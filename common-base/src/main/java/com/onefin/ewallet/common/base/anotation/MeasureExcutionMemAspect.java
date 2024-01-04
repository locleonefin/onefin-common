package com.onefin.ewallet.common.base.anotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Slf4j
@Aspect
public class MeasureExcutionMemAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeasureExcutionMemAspect.class);

	@Around("@annotation(com.onefin.ewallet.common.base.anotation.MeasureExcutionMem)")
	public Object measureTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Runtime rt = Runtime.getRuntime();
		long total_mem = rt.totalMemory();
		long free_mem = rt.freeMemory();
		long used_mem = total_mem - free_mem;
		LOGGER.info("Before execution, application consumed [{}]", String.format("%,d", used_mem));
		Object proceed = proceedingJoinPoint.proceed();
		total_mem = rt.totalMemory();
		free_mem = rt.freeMemory();
		used_mem = total_mem - free_mem;
		LOGGER.info("Method [{}] after execution consumed [{}]", proceedingJoinPoint.getSignature().getName(), String.format("%,d", used_mem));
		return proceed;
	}
}
