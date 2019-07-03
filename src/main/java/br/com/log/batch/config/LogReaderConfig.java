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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.log.batch.model.LogLine;
import br.com.log.batch.step.chunk.LogLineReader;
import br.com.log.batch.step.chunk.LogLineWriter;
import br.com.log.batch.step.tasklet.ExecuteQueryTasklet;

@Configuration
public class LogReaderConfig {

	@Value("${startDate}")
	private String startDate;
	
	@Value("${duration}")
	private String duration;
	
	@Value("${threshold}")
	private int threshold;
	
	@Value("${logPath}")
	private String logPath;
	
	@Autowired
	private JobBuilderFactory jobBuilder;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job readLogJob() {
		return this.jobBuilder.get("readLogJob")
			                  .start(processLines(logLineReader(), logLineWriter())).on(ExitStatus.COMPLETED.getExitCode())
			                  .to(executeQuery()).end()
			                  .build();
	}
	
	@Bean
    protected Step processLines(ItemReader<LogLine> reader,  ItemWriter<LogLine> writer) {
        return stepBuilderFactory.get("processLines")
			        			 .<LogLine, LogLine> chunk(5)
					             .reader(reader)
					             .writer(writer)
					             .build();
    }
	
	@Bean
    public Step executeQuery() {
    	return stepBuilderFactory.get("executeQueryStep")
								 .tasklet(getExecuteQueryTasklet())
								 .build();
    }
	
    @Bean
    public ItemReader<LogLine> logLineReader() {
    	return new LogLineReader();
    }
    
    @Bean
    public ItemWriter<LogLine> logLineWriter() {
    	return new LogLineWriter();
    }
    
    @Bean
    public Tasklet getExecuteQueryTasklet() {
    	return new ExecuteQueryTasklet();
    }
	
}
