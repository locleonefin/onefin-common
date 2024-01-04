package com.onefin.ewallet.common.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserAgentInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
//		resolver.setFallbackPageable(PageRequest.of(0, Integer.MAX_VALUE)); // default Pageable
//		resolver.setMaxPageSize(Integer.MAX_VALUE); // maximum page size
		resolvers.add(resolver);
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}
}
