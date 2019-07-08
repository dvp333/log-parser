package com.ef.parser;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ef.parser.validation.DatePattern;

@Component
public class Arguments {

	@Value("${startDate}")
	@DatePattern(message="Invalid startDate format. Must be yyyy-MM-dd.HH:mm:ss.")
	private String startDate;
	
	@Value("${duration}")
	@Pattern(regexp="hourly|daily", message="Invalid duration value. Must be hourly or daily")
	private String duration;
	
	@Value("${threshold}")
	@Positive(message="Invalid threshold value. Must be a positive number")
	private Integer threshold;
	
	@Value("${logPath}")
	@NotEmpty(message="The logpath argument is required")
	private String logPath;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
}
