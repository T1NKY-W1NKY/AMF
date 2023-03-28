package com.example.ApexMapFinder.service;

import com.example.ApexMapFinder.dao.NotificationDAO;
import com.example.ApexMapFinder.dto.Gamemode;
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
    private final boolean CURRENT_MAP = true;
    private final boolean NEXT_MAP = false;
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

        log.info("Finding maps with lowest time to update...");
        List<String> nextGamemodesToUpdate = new ArrayList<>();
        Map<GamemodeEnum, String> nextMapsToUpdate = new HashMap();
        HashMap<String, Long> gamemodeTimes = amfService.getEndTimer();
        log.info("Map times: " +  gamemodeTimes.toString());
        List<Long> times = new ArrayList<>( gamemodeTimes.values());
        long lowestTime = times.get(0);
        int index = 0;
        for (int i = 1; i < times.size(); i++) {
            if (times.get(i) < lowestTime) {
                lowestTime = times.get(i);
            }
        }
        log.info("Lowest time: " + lowestTime);


        //Finds the gamemods with the lowest update times
        long finalLowestTime = lowestTime;
         gamemodeTimes.forEach((gamemode, time) ->
            {
                if(time.equals(finalLowestTime)){
                    nextGamemodesToUpdate.add(gamemode);
                }
            });
         log.info("Gamemodes to next be updated: " + nextGamemodesToUpdate.toString());

         //Finds the maps with the lowest update times
        for(String gamemode : nextGamemodesToUpdate){
            GamemodeEnum gamemodeEnum = GamemodeEnum.findByName(gamemode);
            nextMapsToUpdate.put((gamemodeEnum), amfService.getMapName(NEXT_MAP, gamemodeEnum));
        }
        log.info("Maps to be next updated: " + nextMapsToUpdate.values());
        log.info("Exiting getNextMapsToChange()...");

        return nextMapsToUpdate;
    }

    //Could probably clean this up with Streams
    public void sendMapChangeEmail() throws MessagingException {
        List<MapEnum> nextMapEnums = new ArrayList<>();
        log.info("Entering getNextMapsToChange()...");
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

        log.info("Next gamemodes to change: " + nextMaps.keySet().toString() + " | Next maps to change: " + nextMapEnums.toString());

        //checks all users to see if the maps they wish to be notified on are the next maps to be changed
        Multimap<String, MapEnum> notificationMutliMap = ArrayListMultimap.create();
        for(Notification user : notifications){
            for(MapEnum userMap : user.getGameMaps()){
                for(MapEnum map : nextMapEnums){
                    if(userMap == map){
                        notificationMutliMap.put(user.getEmail(), map);
                    }
                }
            }
        }

        //Sends an email to users who have chosen to be notified on the next maps to be changed
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
