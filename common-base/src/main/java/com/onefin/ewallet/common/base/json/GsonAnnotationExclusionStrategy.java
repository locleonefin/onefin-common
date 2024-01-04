package com.onefin.ewallet.common.base.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.onefin.ewallet.common.base.anotation.GsonJsonIgnore;

public class GsonAnnotationExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(GsonJsonIgnore.class) != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}
}