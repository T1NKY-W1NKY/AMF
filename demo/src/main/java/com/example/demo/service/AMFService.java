package com.example.demo.service;

import com.example.demo.dao.AMFSQLDAO;
import com.example.demo.dto.AMF;
import com.example.demo.dto.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
public class AMFService {

    //Creates single, null amf object and gets json payload to be read for amf obj
    @Autowired
    private AMFSQLDAO amfDAO;
    private AMF amf = null;
    WebClient webClient = WebClient.create();
    String jsonString = webClient.get()
            .uri("https://api.mozambiquehe.re/maprotation?version=2&auth=XxaZO6hTfymkQoBqNqlg")
            .exchange()
            .block()
            .bodyToMono(String.class)
            .block();

    //Maps json response to amf (kinda hacky, should already have amf object defined before method)
    public AMF getAmf() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            amf = mapper.readValue(jsonString, AMF.class);
            amfDAO.save(amf);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        return amf;
    }

    //need case for when newAmf returns null
    public AMF updateAmf(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            amf = mapper.readValue(jsonString, AMF.class);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        return amfDAO.update(4, amf);
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

        //default image b/c i dont have all the maps in the hashmap
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

    public Player getPlayer(String name){

        /* Is there a cleaner way to do all this?? ************************************************/
        String playerJson = webClient.get()
                .uri("https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=" + name + "&auth=XxaZO6hTfymkQoBqNqlg")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        String playerData = playerJson;
        Player player = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            player = mapper.readValue(playerJson, Player.class);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        /* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ ***************************************************/

        return player;
    }

}
