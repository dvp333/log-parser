package br.com.log.batch.step.chunk;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.log.batch.model.LogLine;
import br.com.log.batch.repository.LogRepository;

public class LogLineWriter implements ItemWriter<LogLine>, StepExecutionListener {

	private static final Log logger = LogFactory.getLog("log.reader");
	
	@Autowired
	private LogRepository repo;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void write(List<? extends LogLine> items) throws Exception {
		logger.info("====== Gravando no banco " + items.size() + " items !!!");
		items.stream().forEach(item -> repo.save(item));
	}


	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return ExitStatus.COMPLETED;
	}

}
