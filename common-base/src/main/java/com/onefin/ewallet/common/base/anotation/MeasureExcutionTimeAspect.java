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
public class MeasureExcutionTimeAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeasureExcutionTimeAspect.class);

	@Around("@annotation(com.onefin.ewallet.common.base.anotation.MeasureExcutionTime)")
	public Object measureTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object proceed = proceedingJoinPoint.proceed();
		stopWatch.stop();
		LOGGER.info("Method [{}] executed [{}] ms", proceedingJoinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
		return proceed;
	}
}
