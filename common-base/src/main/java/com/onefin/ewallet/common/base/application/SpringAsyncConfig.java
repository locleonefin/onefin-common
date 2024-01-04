package com.onefin.ewallet.common.base.application;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.onefin.ewallet.common.base.errorhandler.AsyncExceptionHandler;

/**
 * @author thaita
 */
@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {

	@Bean(name = "asyncExecutor")
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(50);
		executor.setMaxPoolSize(60);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("== AsynchThread-");
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncExceptionHandler();
	}

}