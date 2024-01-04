package com.onefin.ewallet.common.base.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.onefin.ewallet.common.base.model.RestProxy;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component("restTemplateHelper")
public class RestTemplateHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateHelper.class);
	private final static String DEFAULT_FORMAT_DATE = "yyyy-MM-dd HH-mm-ss";

	// RestTemplate Config
	public static final int connectTimeout = 120000;
	public static final int readTimeout = 120000;

	public RestTemplate build(RestProxy configProx) {
		CredentialsProvider credsProvider = null;
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		if (configProx != null && configProx.isActive()) {
			credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(configProx.getHost(), configProx.getPort()), new UsernamePasswordCredentials(configProx.getUserName(), configProx.getPassword()));

			clientBuilder.useSystemProperties();
			clientBuilder.setProxy(new HttpHost(configProx.getHost(), configProx.getPort()));
			clientBuilder.setDefaultCredentialsProvider(credsProvider).setRetryHandler((exception, executionCount, context) -> {
				if (executionCount > 3) {
					LOGGER.error("Maximum tries reached for client http pool ");
					return false;
				}
				if (exception instanceof org.apache.http.NoHttpResponseException) {
					LOGGER.error("No response from server on " + executionCount + " call");
					return true;
				}
				return false;
			});
			clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
			if (configProx.isAuth()) {
				credsProvider.setCredentials(new AuthScope(configProx.getHost(), configProx.getPort()), new UsernamePasswordCredentials(configProx.getUserName(), configProx.getPassword()));
				clientBuilder.setDefaultCredentialsProvider(credsProvider).setRetryHandler((exception, executionCount, context) -> {
					if (executionCount > 3) {
						LOGGER.error("Maximum tries reached for client http pool ");
						return false;
					}
					if (exception instanceof org.apache.http.NoHttpResponseException) {
						LOGGER.error("No response from server on " + executionCount + " call");
						return true;
					}
					return false;
				});
			}
		}

		CloseableHttpClient httpClient = clientBuilder.build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		restTemplate.setMessageConverters(Collections.<HttpMessageConverter<?>>singletonList(buildMessageConverter()));

		return restTemplate;
	}

	public RestTemplate buildForHttps(RestProxy configProx) {
		CredentialsProvider credsProvider = null;
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();

			if (configProx != null && configProx.isActive()) {
				credsProvider = new BasicCredentialsProvider();
				clientBuilder.useSystemProperties();
				clientBuilder.setProxy(new HttpHost(configProx.getHost(), configProx.getPort()));
				clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
				if (configProx.isAuth()) {
					credsProvider.setCredentials(new AuthScope(configProx.getHost(), configProx.getPort()), new UsernamePasswordCredentials(configProx.getUserName(), configProx.getPassword()));
					clientBuilder.setDefaultCredentialsProvider(credsProvider).setRetryHandler((exception, executionCount, context) -> {
						if (executionCount > 3) {
							LOGGER.error("Maximum tries reached for client http pool ");
							return false;
						}
						if (exception instanceof org.apache.http.NoHttpResponseException) {
							LOGGER.error("No response from server on " + executionCount + " call");
							return true;
						}
						return false;
					});
				}
			}

			CloseableHttpClient httpClient = clientBuilder.setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(connectTimeout);
			requestFactory.setConnectTimeout(readTimeout);

			requestFactory.setHttpClient(httpClient);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			restTemplate.setMessageConverters(Collections.<HttpMessageConverter<?>>singletonList(buildMessageConverter()));

			return restTemplate;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			LOGGER.error(e.getMessage());
		}

		return null;
	}

	private MappingJackson2HttpMessageConverter buildMessageConverter() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new Jackson2HalModule());
		mapper.setDateFormat(new SimpleDateFormat(DEFAULT_FORMAT_DATE));
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		// converter.setSupportedMediaTypes(MediaType.parseMediaTypes(MediaTypes.HAL_JSON_VALUE));
		converter.setObjectMapper(mapper);

		return converter;
	}

	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, RestProxy configProx, ParameterizedTypeReference<T> type, HttpEntity<?> entity) {
		RestTemplate restTemplate = null;

		if (isContainHttps(url)) {
			restTemplate = buildForHttps(configProx);
		} else {
			restTemplate = build(configProx);
		}

		Assert.notNull(restTemplate, "RestTemplate is not constructed. It cannot be null.");

		return restTemplate.exchange(url, method, entity, type);
	}

	public <T> ResponseEntity<T> get(String path, HttpHeaders httpHeaders, List<String> pathVariables, Map<String, String> urlParameters, RestProxy configProx, ParameterizedTypeReference<T> type) {
		String url = buildUrl(path, pathVariables, urlParameters);
		HttpEntity<MultiValueMap<String, ?>> requestEntity = new HttpEntity<MultiValueMap<String, ?>>(httpHeaders);

		return exchange(url, HttpMethod.GET, configProx, type, requestEntity);
	}

	public <T> ResponseEntity<T> get(String path, Map<String, String> headers, List<String> pathVariables, Map<String, String> urlParameters, RestProxy configProx, ParameterizedTypeReference<T> type) {
		HttpHeaders httpHeaders = buildHeader(headers);
		String url = buildUrl(path, pathVariables, urlParameters);
		HttpEntity<MultiValueMap<String, ?>> requestEntity = new HttpEntity<MultiValueMap<String, ?>>(httpHeaders);

		return exchange(url, HttpMethod.GET, configProx, type, requestEntity);
	}

	@SuppressWarnings("unchecked")
	public <T> ResponseEntity<T> post(String path, String contentType, Map<String, String> headers, List<String> pathVariables, Map<String, String> urlParameters, RestProxy configProx, Object body, ParameterizedTypeReference<T> type) {
		HttpHeaders httpHeaders = buildHeader(headers);

		String url = buildUrl(path, pathVariables, urlParameters);

		if (MediaType.MULTIPART_FORM_DATA_VALUE.equals(contentType)) {
			MultiValueMap<String, Object> multipartMap = new LinkedMultiValueMap<String, Object>();
			if (body != null && body instanceof Map) {
				((Map<String, Object>) body).entrySet().forEach(entry -> {
					multipartMap.add(entry.getKey(), entry.getValue());
				});
			}

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(multipartMap, httpHeaders);
			return exchange(url, HttpMethod.POST, configProx, type, requestEntity);
		} else {
			HttpEntity<Object> requestEntity = null;
			if (body != null) {
				requestEntity = new HttpEntity<Object>(body, httpHeaders);
			} else {
				requestEntity = new HttpEntity<>(httpHeaders);
			}

			return exchange(url, HttpMethod.POST, configProx, type, requestEntity);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> ResponseEntity<T> patch(String path, String contentType, Map<String, String> headers, List<String> pathVariables, Map<String, String> urlParameters, RestProxy configProx, Object body, ParameterizedTypeReference<T> type) {
		HttpHeaders httpHeaders = buildHeader(headers);

		String url = buildUrl(path, pathVariables, urlParameters);

		if (MediaType.MULTIPART_FORM_DATA_VALUE.equals(contentType)) {
			MultiValueMap<String, Object> multipartMap = new LinkedMultiValueMap<String, Object>();
			if (body != null && body instanceof Map) {
				((Map<String, Object>) body).entrySet().forEach(entry -> {
					multipartMap.add(entry.getKey(), entry.getValue());
				});
			}

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(multipartMap, httpHeaders);
			return exchange(url, HttpMethod.POST, configProx, type, requestEntity);
		} else {
			HttpEntity<Object> requestEntity = null;
			if (body != null) {
				requestEntity = new HttpEntity<Object>(body, httpHeaders);
			} else {
				requestEntity = new HttpEntity<>(httpHeaders);
			}

			return exchange(url, HttpMethod.PATCH, configProx, type, requestEntity);
		}
	}

	public <T> ResponseEntity<T> delete(String path, Map<String, String> headers, List<String> pathVariables, Map<String, String> urlParameters, RestProxy configProx, ParameterizedTypeReference<T> type) {
		HttpHeaders httpHeaders = buildHeader(headers);
		String url = buildUrl(path, pathVariables, urlParameters);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(httpHeaders);
		return exchange(url, HttpMethod.DELETE, configProx, type, requestEntity);
	}

	@SuppressWarnings("unchecked")
	public <T> ResponseEntity<T> put(String path, String contentType, Map<String, String> headers, List<String> pathVariables, Map<String, String> urlParameters, RestProxy configProx, Object body, ParameterizedTypeReference<T> type) {
		HttpHeaders httpHeaders = buildHeader(headers);
		String url = buildUrl(path, pathVariables, urlParameters);

		if (MediaType.MULTIPART_FORM_DATA_VALUE.equals(contentType)) {
			MultiValueMap<String, Object> multipartMap = new LinkedMultiValueMap<String, Object>();
			if (body != null && body instanceof Map) {
				((Map<String, Object>) body).entrySet().forEach(entry -> {
					multipartMap.add(entry.getKey(), entry.getValue());
				});
			}

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(multipartMap, httpHeaders);
			return exchange(url, HttpMethod.PUT, configProx, type, requestEntity);
		} else {
			HttpEntity<?> requestEntity = null;

			if (body != null) {
				requestEntity = new HttpEntity<>(body, httpHeaders);
			} else {
				requestEntity = new HttpEntity<>(httpHeaders);
			}

			return exchange(url, HttpMethod.PUT, configProx, type, requestEntity);
		}
	}

	public HttpHeaders buildHeader(Map<String, String> headers) {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (headers != null) {
			headers.entrySet().forEach(entry -> {
				httpHeaders.set(entry.getKey(), entry.getValue());
			});
		}
		httpHeaders.set("User-Agent", "OneFin Service");
		return httpHeaders;
	}

	public HttpHeaders buildHeaderWithToken(String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.AUTHORIZATION, token);

		return httpHeaders;
	}

	public String buildUrl(String path, List<String> pathVariables, Map<String, String> urlParameters) {
		if (!isValidUrl(path)) {
			path = "http://" + path;
		}

		if (pathVariables != null) {
			for (String value : pathVariables) {
				path = path.replaceFirst("\\{.*?\\}", value);
			}
		}

		boolean isFirst = true;

		if (urlParameters != null) {
			for (Entry<String, String> entry : urlParameters.entrySet()) {
				if (isFirst) {
					path = path + "?" + entry.getKey() + "=" + entry.getValue();
					isFirst = false;
				} else {
					path = path + "&" + entry.getKey() + "=" + entry.getValue();
				}
			}
		}
		return path;
	}

	public boolean isValidUrl(String url) {
		return isContainHttp(url) || isContainHttps(url);
	}

	public boolean isContainHttp(String url) {
		return url.toLowerCase().startsWith("http://");
	}

	public boolean isContainHttps(String url) {
		return url.toLowerCase().startsWith("https://");
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> ResponseEntity<String> postUpload(String path,
																String fileName,
																List<String> pathVariables,
																byte[] fileInput,
																Map<String, String> urlParameters,
																RestProxy configProx) {


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		// This nested HttpEntiy is important to create the correct
		// Content-Disposition entry with metadata "name" and "filename"
		MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
		ContentDisposition contentDisposition = ContentDisposition
				.builder("form-data")
				.name("file")
				.filename(fileName)
				.build();
		fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
		HttpEntity<byte[]> fileEntity = new HttpEntity<>(fileInput, fileMap);

		RestTemplate restTemplate;
		if (isContainHttps(path)) {
			restTemplate = buildForHttps(configProx);
		} else {
			restTemplate = build(configProx);
		}
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//		restTemplate = new RestTemplate();

		String url = buildUrl(path, pathVariables, urlParameters);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", fileEntity);

		HttpEntity<MultiValueMap<String, Object>> requestEntity =
				new HttpEntity<>(body, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(
					url,
					HttpMethod.POST,
					requestEntity,
					String.class);

			return response;
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return null;
		}
	}
}
