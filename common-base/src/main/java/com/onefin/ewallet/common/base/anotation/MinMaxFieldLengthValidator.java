package com.onefin.ewallet.common.base.anotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinMaxFieldLengthValidator implements ConstraintValidator<MinMaxFieldLengthConstraint, String> {

	private int maxLength;

	private int minLength;
	private boolean checkNull;
	private boolean checkMaxLength;

	@Override
	public void initialize(MinMaxFieldLengthConstraint constraintAnnotation) {
		this.maxLength = constraintAnnotation.maxLength();
		this.minLength = constraintAnnotation.minLength();
		this.checkNull = constraintAnnotation.checkNull();
		this.checkMaxLength = constraintAnnotation.checkMaxLength();
	}

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		if (name == null){
			return !checkNull;
		}
		if (checkMaxLength) {
			return name.length() >= minLength && name.length() <= maxLength;
		} else {
			return name.length() >= minLength;
		}
	}
}
