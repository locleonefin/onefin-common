package com.onefin.ewallet.common.utility.dto;

import com.onefin.ewallet.common.utility.json.JSONHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("MappingHelper")
public class MappingHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(MappingHelper.class);

	public boolean validateMapping(Class<?> sourceClass, Class<?> destinationClass) throws NoSuchFieldException {

		Field[] sourceFields = sourceClass.getDeclaredFields();
		Field[] destinationFields = destinationClass.getDeclaredFields();

		Set<String> destinationFieldNames = new HashSet<>();

		for (Field sourceField : destinationFields) {
			destinationFieldNames.add(sourceField.getName());
		}

		List<String> missingField = new ArrayList<>();
		boolean listStatus = true;

		for (Field field : sourceFields) {
			if (!destinationFieldNames.contains(field.getName())) {
				LOGGER.error("Destination object {} missing {} field on source object {}",
						destinationClass.getName(),field.getName(), sourceClass.getName());
				missingField.add(field.getName());
			}else{
				if (field.getType().getName().equals(List.class.getName())){
					LOGGER.info("Found List Type field {} with type parameter in both {} and {}",field.getName(),
							destinationClass.getName(), sourceClass.getName());
					boolean statusListMapping = validateMapping((Class<?>) ((ParameterizedType) field.getGenericType())
									.getActualTypeArguments()[0],
							(Class<?>) ((ParameterizedType) destinationClass
									.getDeclaredField(field.getName()).getGenericType())
									.getActualTypeArguments()[0]);
					if (!statusListMapping){
						listStatus = false;
					}
				}
			}
		}
		return missingField.isEmpty() && listStatus;// All destination fields have corresponding source fields
	}
}
