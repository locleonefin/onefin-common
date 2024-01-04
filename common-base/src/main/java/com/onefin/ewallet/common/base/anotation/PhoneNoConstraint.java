package com.onefin.ewallet.common.base.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNoValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNoConstraint {
	String message() default "Invalid phone number.";

	boolean checkNull() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int mode() default 0;
}