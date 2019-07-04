package br.com.log.batch.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.log.batch.model.AccessLog;
import br.com.log.batch.step.chunk.AccessLogReader;
import br.com.log.batch.step.chunk.AccessLogLineWriter;
import br.com.log.batch.step.tasklet.SearchAccessLogTasklet;

@Configuration
public class AccessLogReaderConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilder;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job readLogJob() {
		return this.jobBuilder.get("readAccessLogJob")
			                  .start(processAccessLog(logLineReader(), logLineWriter())).on(ExitStatus.COMPLETED.getExitCode())
			                  .to(executeQuery()).end()
			                  .build();
	}
	
	@Bean
    protected Step processAccessLog(ItemReader<AccessLog> reader,  ItemWriter<AccessLog> writer) {
        return stepBuilderFactory.get("processLines")
			        			 .<AccessLog, AccessLog> chunk(1000)
					             .reader(reader)
					             .writer(writer)
					             .build();
    }
	
	@Bean
    public Step executeQuery() {
    	return stepBuilderFactory.get("searchAccessLogStep")
								 .tasklet(getExecuteQueryTasklet())
								 .build();
    }
	
    @Bean
    public ItemReader<AccessLog> logLineReader() {
    	return new AccessLogReader();
    }
    
    @Bean
    public ItemWriter<AccessLog> logLineWriter() {
    	return new AccessLogLineWriter();
    }
    
    @Bean
    public Tasklet getExecuteQueryTasklet() {
    	return new SearchAccessLogTasklet();
    }
	
}
