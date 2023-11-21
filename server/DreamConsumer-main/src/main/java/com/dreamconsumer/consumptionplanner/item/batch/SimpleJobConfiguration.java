package com.dreamconsumer.consumptionplanner.item.batch;

import com.dreamconsumer.consumptionplanner.item.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemRepository itemRepository;

    @Bean
    public Job deleteJob() {
        return jobBuilderFactory.get("deleteJob")
                .start(deleteOld())
                .on("FAILED")
                .stopAndRestart(deleteOld())
                .on("*")
                .end()
                .end()
                .build();
    }

    @Bean
    public Step deleteOld() {
        return stepBuilderFactory.get("deleteOld")
                .tasklet(new ItemTasklet(itemRepository))
                .build();
    }
}