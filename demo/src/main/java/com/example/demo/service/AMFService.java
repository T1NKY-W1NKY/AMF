package com.example.demo.service;

import com.example.demo.dao.AMFSQLDAO;
import com.example.demo.dto.AMF;
import com.example.demo.dto.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AMFService {

    @Autowired
    private AMFSQLDAO amfDAO;
    private AMF amf = null;
    WebClient webClient = WebClient.create();
    private static final Logger log = LoggerFactory.getLogger(AMFService.class);


    //returns amf from db
    public AMF getAMF() {
        return amfDAO.getAMF();
    }



    //Maps json response to null amf obj in service class (should already have amf object defined before method?)
    //need case for when newAmf returns null orrrrrrrr maybe not
    @PostConstruct //runs method on startup
    public AMF updateAMF(){
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = webClient.get()
                .uri("https://api.mozambiquehe.re/maprotation?version=5&auth=XxaZO6hTfymkQoBqNqlg")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        try {
            amf = mapper.readValue(jsonString, AMF.class);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        return amfDAO.updateAMF(amf);
    }



    //TODO: add functionality for arenas or br?
    //returns string of image address for the current or next BATTLE_ROYALE map (specified)
    //state = current / next
    public String getMapImage(String state){
        //could be a problem if amf object is never created since getAmf needs to be called for it to be populated
        String map;

        //does not cover if string equals anything besides current/next
        if(state.equalsIgnoreCase("current")) {
            map = amf.getBattleRoyale().getCurrent().getMap();
        }
        else {
            map = amf.getBattleRoyale().getNext().getMap();
        }

        //default image in case map image not found
        String mapImage = "placeholder.jpg";

        //Create a hashmap where (k) is map name and (v) is image location
        HashMap<String, String> mapImages = new HashMap<>();
        mapImages.put("Kings Canyon", "kingsCanyon.jpg");
        mapImages.put("Olympus","olympus.jpg");
        mapImages.put("Overflow","overflow.jpg");
        mapImages.put("Party Crasher","partyCrasher.jpg");
        mapImages.put("Phase runner","phaseRunner.jpg");
        mapImages.put("World's Edge","worldsEdge.jpg");

        //loop through hashmap to find specified map in it
        for(var entry: mapImages.entrySet()){
            if(entry.getKey().equalsIgnoreCase(map)){
                mapImage = entry.getValue();
            }
        }
        return mapImage;
    }



    //split to updatePlayer & getPlayer
    public Player getPlayer(String name){

        //Look into httpclient instead of webclient | https://www.baeldung.com/httpclient4
        String playerJson = webClient.get()
                .uri("https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=" + name + "&auth=XxaZO6hTfymkQoBqNqlg")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        Player player = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            player = mapper.readValue(playerJson, Player.class);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }

        return player;
    }



    public long timeToSeconds(String time) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(time);
        long seconds = date.getTime() / 1000L;
        return seconds;
    }



    public Long getNextMapTime() throws ParseException {
        AMF amf = amfDAO.getAMF();
        List<Long> timeInSeconds = new ArrayList<>();
        List<String> remainingTimes = new ArrayList<>();

        remainingTimes.add(amf.getArenas().getCurrent().getRemainingTimer());
        remainingTimes.add(amf.getArenasRanked().getCurrent().getRemainingTimer());
        remainingTimes.add(amf.getBattleRoyale().getCurrent().getRemainingTimer());

        for (int i = 0; i < remainingTimes.size(); i++) {
            timeInSeconds.add(timeToSeconds(remainingTimes.get(i)));
        }
        long lowestTime = timeInSeconds.get(0);
        for (int i = 1; i < timeInSeconds.size(); i++) {
            if (timeInSeconds.get(i) < lowestTime) {
                lowestTime = timeInSeconds.get(i);
            }
        }
        log.info(String.valueOf(timeInSeconds));

        //adds a slight update delay in case the apex api takes a moment to update itself (rounds up to nearest 10s place)
        //increases update delay by a maximum of ten seconds; could be a problem is there is not update delay and errors occurs
        long roundUp = 10 - lowestTime % 10;
        log.info((String.valueOf((lowestTime + roundUp) * 100)));

        //* 1000 to go to milliseconds from seconds
        return (lowestTime + roundUp) * 1000;
    }
}
