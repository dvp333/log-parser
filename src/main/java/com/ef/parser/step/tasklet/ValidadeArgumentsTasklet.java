package com.ef.parser.step.tasklet;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ef.parser.Arguments;

public class ValidadeArgumentsTasklet implements Tasklet, StepExecutionListener {

	private static final Log logger = LogFactory.getLog(ValidadeArgumentsTasklet.class);
	
	
	@Autowired
	private Arguments arguments;
	private Validator validator;
	private Set<ConstraintViolation<Arguments>> violations;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("Validating arguments");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		(violations = validator.validate(arguments)).stream().forEach(violation -> logger.error(violation.getMessage()));
		return RepeatStatus.FINISHED;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return violations.isEmpty() ? ExitStatus.COMPLETED : ExitStatus.FAILED;
	}
	
}
