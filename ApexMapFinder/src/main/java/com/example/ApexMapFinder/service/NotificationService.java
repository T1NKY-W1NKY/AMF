package com.example.ApexMapFinder.service;

import com.example.ApexMapFinder.dao.NotificationDAO;
import com.example.ApexMapFinder.dto.GameMap;
import com.example.ApexMapFinder.dto.GamemodeEnum;
import com.example.ApexMapFinder.dto.MapEnum;
import com.example.ApexMapFinder.dto.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
    public Map<GamemodeEnum, String> getNextMapsToChange(){

        List<String> nextGamemodesToUpdate = new ArrayList<>();
        Map<GamemodeEnum, String> nextMapsToUpdate = new HashMap();
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
                nextGamemodesToUpdate.add(k);
            }
        });

        for(String gamemode : nextGamemodesToUpdate){
            nextMapsToUpdate.put(GamemodeEnum.findByName(gamemode), amfService.getMapName(Boolean.FALSE, GamemodeEnum.findByName(gamemode)));
        }

        return nextMapsToUpdate;
    }

    public void sendMapChangeEmail() throws MessagingException {
        List<MapEnum> nextMapEnums = new ArrayList<>();
        Map<GamemodeEnum, String> nextMaps = getNextMapsToChange();
        log.info(nextMaps.toString());


        List<Notification> notifications = notificationDAO.getAllNotifications();
        for(GamemodeEnum gamemodeEnum : nextMaps.keySet()){
            List<MapEnum> mapEnums = MapEnum.getGamemodeMaps(gamemodeEnum);
            for(MapEnum mapEnum : mapEnums){
               if(nextMaps.get(gamemodeEnum).equals(mapEnum.getName())){
                   nextMapEnums.add(mapEnum);
               }
            }
        }

        log.info(nextMapEnums.toString());

        for(Notification user : notifications){

            for(MapEnum userMap : user.getGameMaps()){
                for(MapEnum map : nextMapEnums){
                    if(userMap == map){
                        System.out.println(user.getEmail() + " - " + map);
                    }
                }
            }
        }

    }
}
