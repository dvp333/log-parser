package com.ef.parser.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.Arguments;
import com.ef.parser.repository.AccessLogRepository;
import com.ef.parser.validation.DatePatternValidator;

@Service
public class AccessLogService {

	private static final int MAX_DAILY_THRESHOLD = 500;
	private static final int MAX_HOURLY_THRESHOLD = 300;
	
	@Autowired
	private Arguments arguments;
	
	@Autowired
	private AccessLogRepository logRepository;
	
	public List<String> findAccessLogs() {
		
		if(arguments.getDuration().equalsIgnoreCase("daily") && arguments.getThreshold() > MAX_DAILY_THRESHOLD)
			throw new RuntimeException(String.format("For daily duration, %s is the threshold limit.", MAX_DAILY_THRESHOLD));
		if(arguments.getDuration().equalsIgnoreCase("hourly") && arguments.getThreshold() > MAX_HOURLY_THRESHOLD)
			throw new RuntimeException(String.format("For hourly duration, %s is the threshold limit.", MAX_HOURLY_THRESHOLD));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePatternValidator.ACCESS_LOG_DATE_PATTERN);
		LocalDateTime startDate = LocalDateTime.parse(arguments.getStartDate(), formatter);
		LocalDateTime endDate = arguments.getDuration().equalsIgnoreCase("daily")
				? startDate.plusDays(1).minusSeconds(1)
				: startDate.plusHours(1).minusSeconds(1);
		
		return logRepository.findIpsByThreshold(startDate, endDate, arguments.getThreshold());
	}
	
}
