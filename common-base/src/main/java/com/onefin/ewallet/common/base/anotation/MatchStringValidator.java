package com.onefin.ewallet.common.base.anotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchStringValidator implements ConstraintValidator<MatchString, String> {

	private String text;

	@Override
	public void initialize(MatchString validDate) {
		this.text = validDate.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

		if (value.equals(text)) {
			return true;
		}
		return false;
	}
}
