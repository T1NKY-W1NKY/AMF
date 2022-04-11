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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulingConfig.class);
    @Autowired
    private AMFService amfService;




    //cron job to update player repository every day
    @Scheduled(cron = "0 0 * * * ?")
    public void scheduledDailyPlayerUpdates(){
        amfService.updateAllPlayers();
    }

    //method to create active countdown timer for maps
//    @Scheduled(fixedDelay = 1000)
//    public void countdownTimer() {
//
//        for(int i = 0; i < endTimer.size(); i++){
//
//            //checks to see if timer data needs to be updated
//            if(endTimer.get(i) <= 0){
//                //updates countdown timer NOTE:
//                //ONLY EFFECTIVE FIRST CALL, THEN REDUNDANT SINCE TASK THAT UPDATES AMF REFRESHES ENDTIMER AS WELL
//                try {
//                    endTimer = amfService.getMapTimes();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//            //counts down timer
//            else{
//                long decrement = endTimer.get(i) - 1;
//                endTimer.set(i, decrement);
//                System.out.println(endTimer.get(i));
//                System.out.println(amfService.stinkyFeet);
//            }
//        }
//    }

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
                        log.info("Attempting to update AMF...\n");
                        amfService.updateAMF();
                        log.info("Updated AMF: " + amfService.getAMF().toString());

                        //updates timer countdown data
//                        try {
//                            endTimer = amfService.getMapTimes();
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                    }
                },
                new Trigger() {
                    @Override
                    public Date nextExecutionTime(TriggerContext context) {

                        Long epochTime = null;
                        try {
                            epochTime = amfService.getNextMapTime(amfService.getMapTimes()) + System.currentTimeMillis();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Instant nextExecutionTime = Instant.ofEpochMilli(epochTime);
                        log.info("Next Execution: " + (Date.from(nextExecutionTime)) + " | " + epochTime);

                        return Date.from(nextExecutionTime);
                    }
                }
        );
    }
}
