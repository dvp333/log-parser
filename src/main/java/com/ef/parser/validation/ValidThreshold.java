package com.ef.parser.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy=ThresholdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidThreshold {

	String message() default "Invalid threshold for inputed duration. Maximum of 500 for daily duration and 300 for hourly. ";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
