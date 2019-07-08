package com.ef.parser.validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DatePatternValidator implements ConstraintValidator<DatePattern, String> {

	public static String ACCESS_LOG_DATE_PATTERN = "yyyy-MM-dd.HH:mm:ss";
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			LocalDateTime.parse(value, DateTimeFormatter.ofPattern(ACCESS_LOG_DATE_PATTERN));
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

}
