package com.ef.parser.step.tasklet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ef.parser.service.AccessLogService;

public class SearchAccessLogTasklet implements Tasklet {
	
	private static final Log logger = LogFactory.getLog(AccessLogService.class);
	
	@Autowired
	private AccessLogService service;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("========== Found IPs ==========");
		service.findAccessLogs().stream().forEach(ip -> logger.info(ip));
		return RepeatStatus.FINISHED;
	}

}
