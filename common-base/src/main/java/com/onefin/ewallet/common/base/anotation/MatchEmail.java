package com.onefin.ewallet.common.base.anotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MatchEmailValidator.class)
@Documented
public @interface MatchEmail {

	String message() default "Email is invalid format";

	boolean checkNull() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}
