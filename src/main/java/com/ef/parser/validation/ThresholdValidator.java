package com.ef.parser.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ef.parser.Arguments;

public class ThresholdValidator implements ConstraintValidator<ValidThreshold, Integer> {

	private static final int MAX_DAILY_THRESHOLD = 500;
	private static final int MAX_HOURLY_THRESHOLD = 300;
	
	@Autowired
	private Arguments arguments;
	
	@Value("${duration}")
	private String duration;
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return arguments.getDuration().equals("daily")
				  ? value <= MAX_DAILY_THRESHOLD
				  : value <= MAX_HOURLY_THRESHOLD;
	}

}
