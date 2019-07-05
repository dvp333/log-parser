package com.ef.parser.validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateTimePatternValidator implements ConstraintValidator<ValidDateTimePattern, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

}
