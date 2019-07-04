package com.ef.parser.config;

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

import com.ef.parser.model.AccessLog;
import com.ef.parser.step.chunk.AccessLogWriter;
import com.ef.parser.step.chunk.AccessLogReader;
import com.ef.parser.step.tasklet.SearchAccessLogTasklet;
import com.ef.parser.step.tasklet.ValidadeArgumentsTasklet;

@Configuration
public class AccessLogReaderConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilder;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job readLogJob() {
		return this.jobBuilder.get("readAccessLogJob")
							  .start(validateArguments()).on(ExitStatus.FAILED.getExitCode()).end()
							  .from(validateArguments()).on(ExitStatus.COMPLETED.getExitCode())
			                  .to(processAccessLog(logLineReader(), logLineWriter())).on(ExitStatus.COMPLETED.getExitCode())
			                  .to(searchAccessLog())
			                  .end()
			                  .build();
	}
	
	@Bean
    protected Step processAccessLog(ItemReader<AccessLog> reader,  ItemWriter<AccessLog> writer) {
        return stepBuilderFactory.get("processAccessLogStep")
			        			 .<AccessLog, AccessLog> chunk(1000)
					             .reader(reader)
					             .writer(writer)
					             .build();
    }
	
	@Bean
    public Step searchAccessLog() {
    	return stepBuilderFactory.get("searchAccessLogStep")
								 .tasklet(getExecuteQueryTasklet())
								 .build();
    }
	
	@Bean
    public Step validateArguments() {
    	return stepBuilderFactory.get("validateArgumentsStep")
								 .tasklet(getValidadeArgumentsTasklet())
								 .build();
    }
	
    @Bean
    public ItemReader<AccessLog> logLineReader() {
    	return new AccessLogReader();
    }
    
    @Bean
    public ItemWriter<AccessLog> logLineWriter() {
    	return new AccessLogWriter();
    }
    
    @Bean
    public Tasklet getExecuteQueryTasklet() {
    	return new SearchAccessLogTasklet();
    }
    
    @Bean
    public Tasklet getValidadeArgumentsTasklet() {
    	return new ValidadeArgumentsTasklet();
    }
	
}
