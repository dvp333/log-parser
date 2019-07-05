package com.ef.parser.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy=DateTimePatternValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateTimePattern {
	String message() default "Invalid DateTime format of argument startDate. Use yyyy-MM-dd.HH:mm:ss.";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
