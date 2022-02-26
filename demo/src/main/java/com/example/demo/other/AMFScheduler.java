package com.example.demo.other;

import com.example.demo.dto.AMF;
import com.example.demo.service.AMFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//@Component
//@Configuration
//@EnableScheduling
public class AMFScheduler {

    @Autowired
    private AMFService amfService;
    private Long time = 2000L;

//cant declare outside method or else null bean
//    private AMF amf = amfService.getAmf();
    //initial delay right away, then fixed based off timer of amf
//    @Scheduled(initialDelay = 0, fixedDelay = 20)
//    public void scheduleTest() {
//        Random r = new Random();
//        char c = (char) (r.nextInt(26) + 'a');
//        System.out.print(c);
//    }

    public long timeToSeconds(String time) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(time);
        long seconds = date.getTime() / 1000L;
        return seconds;
    }
    //initial delay right away, then fixed based off timer of amf
    @Scheduled(initialDelay = 1000, fixedDelay = 10000)
    public void scheduleAMF() throws ParseException {
        AMF amf = amfService.getAmf();
        List<Long> timeInSeconds = new ArrayList<>();
        List<String> remainingTimes = new ArrayList<>();

        remainingTimes.add(amf.getArenas().getCurrent().getRemainingTimer());
        remainingTimes.add(amf.getArenasRanked().getCurrent().getRemainingTimer());
        remainingTimes.add(amf.getBattleRoyale().getCurrent().getRemainingTimer());

        for(int i = 0; i < remainingTimes.size(); i++){
            timeInSeconds.add(timeToSeconds(remainingTimes.get(i)));
        }
        long lowestTime = timeInSeconds.get(0);
        for(int i = 1; i < timeInSeconds.size(); i++){
            if(timeInSeconds.get(i) < lowestTime){
                lowestTime = timeInSeconds.get(i);
            }
        }
        System.out.println(remainingTimes);
        System.out.println(timeInSeconds);
        System.out.println(lowestTime);
    }
}
