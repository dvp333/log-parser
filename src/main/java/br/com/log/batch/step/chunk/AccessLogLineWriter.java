package br.com.log.batch.step.chunk;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.log.batch.model.AccessLog;
import br.com.log.batch.repository.AccessLogRepository;

public class AccessLogLineWriter implements ItemWriter<AccessLog> {

	private static final Log logger = LogFactory.getLog("log.reader");
	
	@Autowired
	private AccessLogRepository repo;
	
	@Override
	public void write(List<? extends AccessLog> items) throws Exception {
		logger.info("Saving to database " + items.size() + " items");
		items.stream().forEach(item -> repo.save(item));
	}

}
