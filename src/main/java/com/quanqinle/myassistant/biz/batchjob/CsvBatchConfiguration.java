package com.quanqinle.myassistant.biz.batchjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.batch.support.DatabaseType;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * CSV文件批处理导入db配置
 * 此注解将使批处理在项目运行时执行
 * @author quanqinle
// FIXME 升级springboot到1.4.3后，运行报错，暂不解决，注释下面两行
 @Configuration
 @EnableBatchProcessing
 */
public class CsvBatchConfiguration {
	Logger logger = LoggerFactory.getLogger(CsvBatchConfiguration.class);

	/**
	 * 参数由Spring自动注入
	 * @param dataSource
	 * @param transactionManager
	 * @return
	 * @throws Exception
	 */
	@Bean
	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {

		logger.info("start-->JobRepository");

		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType(DatabaseType.MYSQL.getProductName());
		return jobRepositoryFactoryBean.getObject();
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) {
		logger.info("start-->JobLauncher");
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		return jobLauncher;
	}

	// tag::readerwriterprocessor[]

	@Bean
	public ItemReader<CsvPerson> csvItemReader() {
		logger.info("start-->ItemReader");

		return new FlatFileItemReader<CsvPerson>() {{
			setResource(new ClassPathResource("batchjob-person.csv"));

			setLineMapper(new DefaultLineMapper<CsvPerson>() {
				{
					setLineTokenizer(new DelimitedLineTokenizer() {{
						setNames("name", "age", "nation", "address");
					}});
					setFieldSetMapper(new BeanWrapperFieldSetMapper<CsvPerson>() {{
						setTargetType(CsvPerson.class);
					}});
				}
			});
		}};
	}

/*
	@Bean
	public ItemReader<CsvPerson> jpaItemReader(EntityManagerFactory entityManagerFactory) {
		JpaPagingItemReader<CsvPerson> itemReader = new JpaPagingItemReader<>();
		itemReader.setEntityManagerFactory(entityManagerFactory);
		itemReader.setQueryString("select u from CsvPerson u");
		return itemReader;
	}
*/

	@Bean
	public ItemProcessor<CsvPerson, CsvPerson> processor() {
		logger.info("start-->ItemProcessor");

		// 自定义的processor
		CsvItemProcessor processor = new CsvItemProcessor();
		// 因自定义processor的缘故，校验器是必须的，否则app无法启动
		processor.setValidator(csvBeanValidator());
		return processor;
/*
		//简便的数据处理方式。不需要自己实现数据校验类
		return item -> {
			logger.info("item-->" + item.getName());
			item.setAge(item.getAge() + 1); // 年龄加1
			return item;
		};*/
	}

	@Bean
	public ItemWriter<CsvPerson> jpaItemWriter(EntityManagerFactory entityManagerFactory) {
		logger.info("start-->ItemWriter");

		JpaItemWriter<CsvPerson> itemWriter = new JpaItemWriter<>();
		itemWriter.setEntityManagerFactory(entityManagerFactory);
		return itemWriter;
	}
	// end::readerwriterprocessor[]

	// tag::jobstep[]

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory,
	                 ItemReader<CsvPerson> csvItemReader,
	                 ItemProcessor<CsvPerson, CsvPerson> processor,
	                 ItemWriter<CsvPerson> jpaItemWriter) {
		logger.info("start-->Step");

		return stepBuilderFactory.get("step1")
				// 每次提交10条数据
				.<CsvPerson, CsvPerson>chunk(10)
				.reader(csvItemReader)
				.processor(processor)
				.writer(jpaItemWriter)
				.build();
	}

	@Bean
	public Job importJob(JobBuilderFactory job, Step step) {
		logger.info("start-->Job");

		return job.get("importJob")
				.incrementer(new RunIdIncrementer())
				.flow(step)
				.end()
//				.listener(new CsvJobListener())
				.listener(csvJobListener())
				.build();
		// end::jobstep[]
	}

	@Bean
	public CsvJobListener csvJobListener() {
		return new CsvJobListener();
	}

	public Validator<CsvPerson> csvBeanValidator() {
		return new CsvBeanValidator<>();
	}
}