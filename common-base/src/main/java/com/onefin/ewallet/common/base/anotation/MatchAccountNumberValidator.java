package com.onefin.ewallet.common.base.anotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchAccountNumberValidator implements ConstraintValidator<MatchAccountNumber, String> {

	private static final String PATTERN = "^[0-9]{1,50}$";
	private boolean checkNull;

	@Override
	public boolean isValid(String accountNumber, ConstraintValidatorContext constraintValidatorContext) {
		if (accountNumber == null) {
			return !checkNull;
		}
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(accountNumber);
		return matcher.matches();
	}

	@Override
	public void initialize(MatchAccountNumber constraintAnnotation) {
		this.checkNull = constraintAnnotation.checkNull();
	}
}
