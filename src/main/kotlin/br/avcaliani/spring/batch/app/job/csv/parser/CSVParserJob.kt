package br.avcaliani.spring.batch.app.job.csv.parser

import br.avcaliani.spring.batch.app.job.csv.parser.model.User
import br.avcaliani.spring.batch.app.job.csv.parser.processor.UserItemProcessor
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.PassThroughLineAggregator
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource


/**
 * This Job will read a CSV file and write it as a JSON.
 */
@Configuration
class CSVParserJob {

    @Autowired
    lateinit var jobFactory: JobBuilderFactory

    @Autowired
    lateinit var stepFactory: StepBuilderFactory

    @Bean
    fun job() = jobFactory.get("csv-parser-job")
        .start(step())
        .build()

    @Bean
    fun step() = stepFactory.get("csv-parser-step")
        .chunk<User, User>(10)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build()

    @Bean
    fun reader() = FlatFileItemReaderBuilder<User>()
        .name("csv-parser-reader")
        .resource(FileSystemResource("DATA/INPUT/users.csv"))
        .delimited()
        .names("firstName", "lastName")
        .targetType(User::class.java)
        .build()

    @Bean
    fun processor() = UserItemProcessor()

    @Bean
    fun writer() = JsonFileItemWriterBuilder<User>()
        .name("csv-parser-writter")
        .jsonObjectMarshaller(JacksonJsonObjectMarshaller())
        .resource(FileSystemResource("DATA/OUTPUT/users.json"))
        .build()
}