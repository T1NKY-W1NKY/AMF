package com.example.ApexMapFinder.service;

import com.example.ApexMapFinder.dao.NotificationDAO;
import com.example.ApexMapFinder.dto.GamemodeEnum;
import com.example.ApexMapFinder.dto.MapEnum;
import com.example.ApexMapFinder.dto.Notification;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;
    @Autowired
    private AMFService amfService;
    @Autowired
    private JavaMailSender javaMailSender;
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

    //Could probably clean this up with Streams
    public void sendMapChangeEmail() throws MessagingException {
        List<MapEnum> nextMapEnums = new ArrayList<>();
        Map<GamemodeEnum, String> nextMaps = getNextMapsToChange();

        //Converts all new maps from strings to their enums
        //Not great implementaion because it iterates through every gamemode and map
        List<Notification> notifications = notificationDAO.getAllNotifications();
        for(GamemodeEnum gamemodeEnum : nextMaps.keySet()){
            //Get all maps for a gamemode
            List<MapEnum> mapEnums = MapEnum.getGamemodeMaps(gamemodeEnum);
            //loops through each map in gamemode
            for(MapEnum mapEnum : mapEnums){
                //checks if this game map is equal to the new gamemode
                if(nextMaps.get(gamemodeEnum).equals(mapEnum.getName())){
                    nextMapEnums.add(mapEnum);
               }
            }
        }

        log.info("Next gamemodes to change: " + nextMaps.keySet().toString() + " | Maps to notify users on: " + nextMapEnums.toString());

        Multimap<String, MapEnum> notificationMutliMap = ArrayListMultimap.create();
        //checks all users to see if they want to be notified on new maps
        for(Notification user : notifications){
            for(MapEnum userMap : user.getGameMaps()){
                for(MapEnum map : nextMapEnums){
                    if(userMap == map){
                        notificationMutliMap.put(user.getEmail(), map);
                    }
                }
            }
        }

        for(String email : notificationMutliMap.keySet()){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("apexmapfinder@gmail.com");
            message.setTo(email);
            message.setSubject("Map Update");

            String mapList = notificationMutliMap.get(email).toString();
//            log.info("New Maps: " + mapList);
//            for(int i = 0; i < notificationMutliMap.get(email).size(); i++){
//                MapEnum map = notificationMutliMap.get(email).
//                mapList += map + ", ";
//
//            }
//            for(MapEnum map : notificationMutliMap.get(email)){
//            }
            message.setText("New Maps: " + mapList);
            javaMailSender.send(message);
        }

    }

}
