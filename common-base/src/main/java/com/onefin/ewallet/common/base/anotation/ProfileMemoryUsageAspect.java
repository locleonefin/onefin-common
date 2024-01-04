package com.onefin.ewallet.common.base.anotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Component
@Aspect
public class ProfileMemoryUsageAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileMemoryUsageAspect.class);
	private static final MemoryMXBean MEMORY_MX_BEAN = ManagementFactory.getMemoryMXBean();

	@Around("@annotation(com.onefin.ewallet.common.base.anotation.ProfileMemoryUsage)")
	public Object profileMemoryUsage(ProceedingJoinPoint joinPoint) throws Throwable {
		MemoryUsage heapMemoryUsageBefore = MEMORY_MX_BEAN.getHeapMemoryUsage();

		LOGGER.info("Method [{}] - Used heap memory before execution: {} MB",
				joinPoint.getSignature().getName(),
				heapMemoryUsageBefore.getUsed() / 1024 / 1024);

		Object result = joinPoint.proceed();

		MemoryUsage heapMemoryUsageAfter = MEMORY_MX_BEAN.getHeapMemoryUsage();

		LOGGER.info("Method [{}] - Used heap memory after execution: {} MB",
				joinPoint.getSignature().getName(),
				heapMemoryUsageAfter.getUsed() / 1024 / 1024);

		long usedHeapMemoryDifference = (heapMemoryUsageAfter.getUsed() - heapMemoryUsageBefore.getUsed()) / 1024 / 1024;
		LOGGER.info("Method [{}] - Heap memory used by execution: {} MB", joinPoint.getSignature().getName(), usedHeapMemoryDifference);

		return result;
	}
}