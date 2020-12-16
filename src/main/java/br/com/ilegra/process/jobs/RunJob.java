package br.com.ilegra.process.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RunJob {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job importRegisterJob;

	@SneakyThrows
	public void run() {
		final JobParameters jobParameter = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();

		jobLauncher.run(importRegisterJob, jobParameter);
	}

}