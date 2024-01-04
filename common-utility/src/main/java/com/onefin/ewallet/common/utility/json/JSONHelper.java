package com.onefin.ewallet.common.utility.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("jsonHelper")
public class JSONHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(JSONHelper.class);

	public Object convertObject2Map(Object data, Class clazzModel) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(mapper.writeValueAsString(data), clazzModel);
		} catch (Exception e) {
			LOGGER.error("Fail to convert Object2Map", e);
			return null;
		}
	}

	public Object convertString2Map(String data, Class clazzModel) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(data, clazzModel);
		} catch (Exception e) {
			LOGGER.error("Fail to convert String2Map", e);
			return null;
		}
	}

	public String convertMap2JsonString(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(data);
		} catch (Exception e) {
			LOGGER.error("Fail to convert Map2JsonString", e);
			return null;
		}
	}

	public <T> Object convertString2MapIgnoreUnknown(String data, Class<T> clazzModel) {
		try {
			ObjectMapper mapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			;
			return mapper.readValue(data, clazzModel);
		} catch (Exception e) {
			LOGGER.error("Fail to convert String2Map", e);
			return null;
		}
	}

	public <T, R> R mappingObject2Instance(T Object, TypeReference<R> typeRef) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(mapper.writeValueAsString(Object), typeRef);
		} catch (Exception e) {
			LOGGER.error("Fail to mapping Object To Instance", e);
			return null;
		}
	}


}