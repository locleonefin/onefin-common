package com.onefin.ewallet.common.base.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinMaxFieldLengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinMaxFieldLengthConstraint {
	String message() default "Invalid name. Please try again";

	boolean checkNull() default true;

	boolean checkMaxLength() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int maxLength() default 255;

	int minLength() default 1;
}

