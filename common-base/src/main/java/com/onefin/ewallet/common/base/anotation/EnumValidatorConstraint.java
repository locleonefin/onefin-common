package com.onefin.ewallet.common.base.anotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, CharSequence> {
	private List<String> acceptedValues;

	private boolean checkNull;

	@Override
	public void initialize(EnumValidator annotation) {
		this.acceptedValues = Stream.of(annotation.enumClass().getEnumConstants()).map(Enum::toString).collect(Collectors.toList());
		this.checkNull = annotation.checkNull();
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null) {
			return !checkNull;
		}
		return acceptedValues.contains(value.toString());
	}
}
