package com.ef.parser.step.chunk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ef.parser.Arguments;
import com.ef.parser.model.AccessLog;

public class AccessLogReader implements ItemReader<AccessLog>, StepExecutionListener {

	private static final Log logger = LogFactory.getLog(AccessLogReader.class);
	
	@Autowired
	private Arguments arguments;
	private BufferedReader reader;
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private Instant start;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
        logger.debug("Log Reader initialized.");
        File file = new File(arguments.getLogPath());
        InputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(inputStream));
			start = Instant.now();
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new RuntimeException(String.format("File not found: ", arguments.getLogPath()));
		}
	}
	
	@Override
	public AccessLog read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		AccessLog accessLog = null;
		String line;
		if ((line = reader.readLine()) != null) {
			String[] logLine = line.split("[|]");
			accessLog = new AccessLog();
			accessLog.setDate(LocalDateTime.parse(logLine[0], dateFormatter));
			accessLog.setIp(logLine[1]);
			accessLog.setRequest(logLine[2]);
			accessLog.setStatus(logLine[3]);
			accessLog.setUserAgent(logLine[4]);
		}
        return accessLog;
	}
	

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		try {
			logger.info("Proccess Duration: " + (Duration.between(start, Instant.now()).toMillis() / 1000) + " seconds");
			reader.close();
		} catch (IOException e) {
			logger.error("Error closing file");
			logger.error(e);
			return ExitStatus.FAILED;
		}
        logger.debug("Line Reader ended.");
		return ExitStatus.COMPLETED;
	}

}
