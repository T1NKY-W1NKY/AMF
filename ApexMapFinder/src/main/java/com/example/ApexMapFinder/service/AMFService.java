package com.example.ApexMapFinder.service;

import com.example.ApexMapFinder.dao.AMFSQLDAO;
import com.example.ApexMapFinder.dao.PlayerDAO;
import com.example.ApexMapFinder.dto.AMF;
import com.example.ApexMapFinder.dto.Gamemode;
import com.example.ApexMapFinder.dto.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@PropertySource("classpath:application.properties")
public class AMFService {

    @Autowired
    private AMFSQLDAO amfDAO;
    @Autowired
    private PlayerDAO playerDAO;
    private AMF amf = null;
    @Value("${API_KEY}")
    private String apiKey;
    WebClient webClient = WebClient.create();
    private static final Logger log = LoggerFactory.getLogger(AMFService.class);
    HashMap<String, Long> endTimer = new HashMap<>();



    //returns amf from db
    public AMF getAMF() {
        return amfDAO.getAMF();
    }

    public HashMap<String, Long> getEndTimer() {
        return endTimer;
    }

    //Maps json response to null amf obj in service class (should already have amf object defined before method?)
    //need case for when newAmf returns null orrrrrrrr maybe not
    @PostConstruct //runs method on startup
    public AMF updateAMF(){
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = webClient.get()
                .uri("https://api.mozambiquehe.re/maprotation?version=5&auth=" + apiKey)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        try {
            amf = mapper.readValue(jsonString, AMF.class);
        } catch (JsonProcessingException jsonProcessingException) {
            log.warn(jsonString);
            jsonProcessingException.printStackTrace();
        }
        //DB check
        return amfDAO.updateAMF(amf);
    }

    @PostConstruct
    public void setEndTimer() throws ParseException {
        endTimer.put("battleRoyale", timeToSeconds(amfDAO.getAMF().getBattleRoyale().getCurrent().getRemainingTimer()));
        endTimer.put("arenas", timeToSeconds(amfDAO.getAMF().getArenas().getCurrent().getRemainingTimer()));
        endTimer.put("arenasRanked", timeToSeconds(amfDAO.getAMF().getArenasRanked().getCurrent().getRemainingTimer()));
        endTimer.put("battleRoyaleRanked", timeToSeconds(amfDAO.getAMF().getRanked().getCurrent().getRemainingTimer()));
    }

    //returns string of image address for the current or next map (specified)
    //state = current / next
    public String getMapImage(String state, String mode){
        //could be a problem if amf object is never created since getAmf needs to be called for it to be populated
        String map;
        Gamemode gamemode;
        if(mode.equalsIgnoreCase("arenas")){
            gamemode = amf.getArenas();
        }
        else if(mode.equalsIgnoreCase("battleRoyale")){
            gamemode = amf.getBattleRoyale();
        }
        else if(mode.equalsIgnoreCase("ranked")){
            gamemode = amf.getRanked();
        }
        else {
            gamemode = amf.getArenasRanked();
        }


        //does not cover if string equals anything besides current/next
        if(state.equalsIgnoreCase("current")) {
            map = gamemode.getCurrent().getMap();
        }
        else {
            map = gamemode.getNext().getMap();
        }

        //default image in case map image not found
        String mapImage = "questionmark_1_1500x843.png";

        //Create a hashmap where (k) is map name and (v) is image location
        HashMap<String, String> mapImages = new HashMap<>();
        mapImages.put("Kings Canyon", "kingsCanyon.jpg");
        mapImages.put("Olympus","olympus.jpg");
        mapImages.put("Overflow","overflow.jpg");
        mapImages.put("Party Crasher","partyCrasher.jpg");
        mapImages.put("Phase runner","phaseRunner.jpg");
        mapImages.put("World's Edge","worldsEdge.webp");
        mapImages.put("Habitat", "habitat.jpg");
        mapImages.put("Storm Point", "stormPoint.png");
        mapImages.put("Drop Off", "dropOff2.jpg");
        mapImages.put("Encore", "encore.jpg");
        mapImages.put("Broken Moon", "broken_moon_2.webp");

        //loop through hashmap to find specified map in it
        for(var entry: mapImages.entrySet()){
            if(entry.getKey().equalsIgnoreCase(map)){
                mapImage = entry.getValue();
            }
        }
        return mapImage;
    }

    //searches amf database and if cant find player there checks the Apex Legends API
    //the logic is fucked since we are using player origin names for everything but they are not being saved to the database currently
    // and have the players use steam so if someone searches a steam players name up, nothing will show
    public Player getPlayerByName(String name){
        Player player = playerDAO.findByPlayerName(name);
        if(player != null){
            return player;
        }
        else{
            return apiPlayerSearch(name);
        }
    }

    public List<Player>  getAllPlayers(){
        return playerDAO.getAllPlayers();
    }


    //does this method save a new player or update an existing one?
    public Player apiPlayerSearch(String name){

        //Look into httpclient instead of webclient | https://www.baeldung.com/httpclient4
        String playerJson = webClient.get()
                .uri("https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=" + name + "&auth=" + apiKey)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        Player player = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            player = mapper.readValue(playerJson, Player.class);
        } catch (JsonProcessingException jsonProcessingException) {
            log.warn(playerJson);
            jsonProcessingException.printStackTrace();
        }

        return player;

    }

    public Player savePlayer(Player player){
        return playerDAO.savePlayer(player);
    }

    //theres no way this actually works because I am limited to 1 request every 2 seconds
    public List<Player> updateAllPlayers(){
        List<Player> allPlayers = playerDAO.getAllPlayers();

        for(Player player : allPlayers){
            String playerJson = webClient.get()
                    .uri("https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=" + player.getGlobal().getName() + "&auth=" + apiKey)
                    .exchange()
                    .block()
                    .bodyToMono(String.class)
                    .block();
            Player updatedPlayer = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                updatedPlayer = mapper.readValue(playerJson, Player.class);
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
            playerDAO.updatePlayer(player.getPlayerId(), updatedPlayer);
        }

        return allPlayers;
    }

    public long timeToSeconds(String time) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(time);
        long seconds = date.getTime() / 1000L;
        return seconds;
    }

    //returns list of map times in raw seconds in set order: 0-Arenas, 1-ArenasRanked, 2-BattleRoyale, 3-BattleRoyaleRanked
    public List<Long> getMapTimes() throws ParseException {
        AMF amf = amfDAO.getAMF();
        List<Long> timeInSeconds = new ArrayList<>();
        List<String> remainingTimes = new ArrayList<>();

        //get map timers in HH:MM:SS
        remainingTimes.add(amf.getArenas().getCurrent().getRemainingTimer());
        remainingTimes.add(amf.getArenasRanked().getCurrent().getRemainingTimer());
        remainingTimes.add(amf.getBattleRoyale().getCurrent().getRemainingTimer());
        remainingTimes.add(amf.getRanked().getCurrent().getRemainingTimer());

        //change timer to seconds
        for (int i = 0; i < remainingTimes.size(); i++) {
            timeInSeconds.add(timeToSeconds(remainingTimes.get(i)));
        }
        return timeInSeconds;
    }

    public Long getNextMapTime(List<Long> timeInSeconds) {

        long lowestTime = timeInSeconds.get(0);
        for (int i = 1; i < timeInSeconds.size(); i++) {
            if (timeInSeconds.get(i) < lowestTime) {
                lowestTime = timeInSeconds.get(i);
            }
        }
        log.info(String.valueOf(timeInSeconds));

        //adds a slight update delay in case the apex api takes a moment to update itself (rounds up to nearest 10s place)
        //increases update delay by a maximum of ten seconds; could be a problem is there is not update delay and errors occurs
//        long roundUp = 10 - lowestTime % 10;
        long roundUp = 7;
        log.info((String.valueOf((lowestTime + roundUp) * 100)));

        //(* 1000) to go to milliseconds from seconds
        return (lowestTime + roundUp) * 1000;
    }

    //used to decrement map countdown timers in service
    public void decrementCountdown(){
        endTimer.replaceAll((k, v) -> v - 1);
    }
}
