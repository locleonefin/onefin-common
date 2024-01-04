package com.onefin.ewallet.common.base.anotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckDateValidator implements ConstraintValidator<CheckDateFormat, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckDateValidator.class);

	private String pattern;

	@Override
	public void initialize(CheckDateFormat constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
		if (value == null) {
			return true;
		}

		try {
			Date date = new SimpleDateFormat(pattern).parse(value);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error: ", e);
			return false;
		}
	}
}
