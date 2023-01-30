package com.example.ApexMapFinder.service;

import com.example.ApexMapFinder.dao.NotificationDAO;
import com.example.ApexMapFinder.dto.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;

@Service
public class NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;
    @Autowired
    private AMFService amfService;
    private static final Logger log = LoggerFactory.getLogger(AMFService.class);

    public void saveNotification(Notification notification) {
        notificationDAO.save(notification);
    }

    public List<Notification> getAllNotifications(){
        return notificationDAO.getAllNotifications();
    }

    public Notification getNotification(String email){
        return notificationDAO.getNotificationByEmail(email);
    }

    public void updateNotification(Notification newNotification){
    }

    public void deleteNotification(String email){
        notificationDAO.delete(email);
    }

    //Find next maps that will update with api call.
    //For use in sending out notifications to notify players of map changes.
    public List<String> getNextMapsToChange(){

        List<String> nextMapsToUpdate = new ArrayList<>();
        HashMap<String, Long> mapTimes = amfService.getEndTimer();
        List<Long> times = new ArrayList<>(mapTimes.values());
        long lowestTime = times.get(0);
        int index = 0;
        for (int i = 1; i < times.size(); i++) {
            if (times.get(i) < lowestTime) {
                lowestTime = times.get(i);
            }
        }



        //Finds the maps with the lowest times
        //which are the next maps to be updated
        long finalLowestTime = lowestTime;
        mapTimes.forEach((k, v) ->
        {
            if(v.equals(finalLowestTime)){
                nextMapsToUpdate.add(k);
            }
        });

        return nextMapsToUpdate;
    }

    public void sendMapChangeEmail(){
        List<String> nextMaps = getNextMapsToChange();
        log.info(nextMaps.toString());
    }
}
