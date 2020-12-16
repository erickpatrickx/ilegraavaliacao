package br.com.ilegra.process.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import br.com.ilegra.process.dto.Report;
import br.com.ilegra.process.dto.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BatchJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job importRegisterJob(final Step myStep) {
		return jobBuilderFactory.get("importRegisterJob").incrementer(new RunIdIncrementer()).flow(myStep).end()
				.build();
	}

	@Bean
	public Step myStep(final ItemReader<Result> registerReaderBatch, final ItemWriter<Result> registerWriterBatch,
			final ItemProcessor<Result, Result> registerProcessorBatch, final TaskExecutor myExecutor) {
		return stepBuilderFactory.get("importBillingLineJob_step1").<Result, Result>chunk(200)
				.reader(registerReaderBatch).processor(registerProcessorBatch).writer(registerWriterBatch)
				.taskExecutor(myExecutor).build();
	}

	@Bean
	public TaskExecutor myExecutor() {
		final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(1);
		return taskExecutor;
	}

}