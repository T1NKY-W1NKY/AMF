package com.example.demo.other;

import com.example.demo.service.AMFService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulingConfig.class);
    @Autowired
    private AMFService amfService;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                new Runnable() {
                    @Override
                    public void run() {
                        amfService.updateAmf();
                    }
                },
                new Trigger() {
                    @Override
                    public Date nextExecutionTime(TriggerContext context) {
                        Optional<Date> lastCompletionTime =
                                Optional.ofNullable(context.lastCompletionTime());
                        Long epochTime = null;
                        try {
                            epochTime = amfService.getNextMapTime() + System.currentTimeMillis();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Instant nextExecutionTime = Instant.ofEpochMilli(epochTime);

//                            nextExecutionTime = lastCompletionTime.orElseGet(Date::new).toInstant()
//                                    .plusMillis(amfService.getNextMapTime());
                        log.info("Time until next execution: " + epochTime);

                        return Date.from(nextExecutionTime);
                    }
                }
        );
    }
}
