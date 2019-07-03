package br.com.log.batch.step.chunk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import br.com.log.batch.model.LogLine;

public class LogLineReader implements ItemReader<LogLine>, StepExecutionListener {

	private static final Log logger = LogFactory.getLog("log.reader");
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public LogLine read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		logger.info("====== LogLineWriter !!!");
		return new LogLine();
	}
	

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return ExitStatus.COMPLETED;
	}

}
