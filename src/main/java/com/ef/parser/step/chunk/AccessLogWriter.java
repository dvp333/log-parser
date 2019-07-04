package com.ef.parser.step.chunk;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.ef.parser.model.AccessLog;
import com.ef.parser.repository.AccessLogRepository;

public class AccessLogWriter implements ItemWriter<AccessLog> {

	private static final Log logger = LogFactory.getLog(AccessLogWriter.class);
	
	@Autowired
	private AccessLogRepository repo;
	
	@Override
	public void write(List<? extends AccessLog> items) throws Exception {
		logger.info(String.format("Saving to database %s items", items.size()));
		items.stream().forEach(item -> repo.save(item));
	}

}
