package com.example.ApexMapFinder.other;

import com.example.ApexMapFinder.dto.Notification;
import com.example.ApexMapFinder.service.AMFService;
import com.example.ApexMapFinder.service.NotificationService;
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

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulingConfig.class);
    @Autowired
    private AMFService amfService;
    @Autowired
    private NotificationService notificationService;

    //cron job to update player repository every day
//    @Scheduled(cron = "0 0 * * * ?")
//    public void scheduledDailyPlayerUpdates(){
//        amfService.updateAllPlayers();
//    }

    //method to create active countdown timer for maps
    @Scheduled(fixedDelay = 1000)
    public void countdownTimer() {
        amfService.decrementCountdown();
    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

//    IF AMF HAS A NULL VALUE THIS IS LIKELY WHY!!!!
//    It happens when both the local and cloud apps are running since they go to access the API at the same time. JSON response:
//    "Error":"Slow down ! You're being throttled. You have 1 requests every 2 seconds allowed, but you're currently at 2 req/s. To upgrade your rate limit, verify your Discord account on https://portal.apexlegendsapi.com/discord-auth"
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("Attempting to update AMF...");
                        amfService.updateAMF();

                        //updates timer countdown data
                        try {
                            amfService.setEndTimer();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Trigger() {
                    @Override
                    public Date nextExecutionTime(TriggerContext context) {

                        Long epochTime = null;
                        try {
                            epochTime = amfService.getNextMapTime(amfService.getMapTimes()) + System.currentTimeMillis();

                            //initiates emails for new maps
                            //commented out for now so I dont' get email
                                //uncomment to resume emailing feature
                                //catch is commented too
                            log.info("Initiating Notification Service...");
                            notificationService.sendMapChangeEmail();
                            log.info("Notification Service Finished.");
                        } catch (ParseException | MessagingException e) {
                            log.warn("Something went wrong while getting map times or in the notification service: ");
                            e.printStackTrace();
                        }

                        Instant nextExecutionTime = Instant.ofEpochMilli(epochTime);
                        log.info("Next Execution (GET request to API): " + (Date.from(nextExecutionTime)) + " | " + epochTime + System.lineSeparator());

                        return Date.from(nextExecutionTime);
                    }
                }
        );
    }
}
