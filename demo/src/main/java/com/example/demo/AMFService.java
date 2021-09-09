package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

@Service
public class AMFService {

    //Creates single amf object and gets json response
    private AMF amf = null;
    WebClient webClient = WebClient.create();
    String jsonString = webClient.get()
            .uri("https://api.mozambiquehe.re/maprotation?version=2&auth=XxaZO6hTfymkQoBqNqlg")
            .exchange()
            .block()
            .bodyToMono(String.class)
            .block();

    //Maps json response to amf (kinda hacky, should already have amf object defined before method)
    public AMF getCurrentAMF() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            amf = mapper.readValue(jsonString, AMF.class);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
    return amf;
    }

    public String getMapImage(){
        amf = getCurrentAMF();
        String currentArenasMap = amf.getArenas().getCurrent().getMap();
        String currentArenasImg = null;

        //Create a hashmap where (k) is map name and (v) is image location
        HashMap<String, String> mapImages = new HashMap<>();
        mapImages.put("Kings Canyon", "kingsCanyon.jpg");
        mapImages.put("Olympus","olympus.jpg");
        mapImages.put("Overflow","overflow.jpg");
        mapImages.put("Party Crasher","partyCrasher.jpg");
        mapImages.put("Phase Runner","phaseRunner.jpg");
        mapImages.put("World's Edge","worldsEdge.jpg");

        for(var entry: mapImages.entrySet()){
            if(entry.getKey().equals(currentArenasMap)){
                currentArenasImg = entry.getValue();
            }
        }


        //then get current map for certain gamemode
        //then return as string or something to pass to controller
        return currentArenasImg;
    }
}
