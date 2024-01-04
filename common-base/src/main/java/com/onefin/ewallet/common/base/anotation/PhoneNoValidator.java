package com.onefin.ewallet.common.base.anotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNoValidator implements ConstraintValidator<PhoneNoConstraint, String> {

	private int mode;
	private boolean checkNull;

	@Override
	public void initialize(PhoneNoConstraint constraintAnnotation) {
		this.mode = constraintAnnotation.mode();
		this.checkNull = constraintAnnotation.checkNull();
	}

	@Override
	public boolean isValid(String phoneNo, ConstraintValidatorContext context) {
		if (phoneNo == null) {
			return !checkNull;
		}
		if (mode == 0 && phoneNo.matches("0\\d{9}")) {
			return true;
		} else if (mode == 1 && phoneNo.matches("84\\d{9}")) {
			return true;
		} else if (mode == 2 && (phoneNo.matches("0\\d{9}") || phoneNo.matches("84\\d{9}"))) {
			return true;
		} else if (mode == 3 && (phoneNo.matches("0\\d{10}") || phoneNo.matches("84\\d{10}"))) {
			return true;
		} else {
			return false;
		}
	}
}
