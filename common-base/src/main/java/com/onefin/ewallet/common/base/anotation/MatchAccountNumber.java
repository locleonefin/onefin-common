package com.onefin.ewallet.common.base.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MatchAccountNumberValidator.class)
@Documented
public @interface MatchAccountNumber {

	String message() default "Account number is invalid format";

	boolean checkNull() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}
