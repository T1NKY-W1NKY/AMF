package com.example.ApexMapFinder.controller;

import com.example.ApexMapFinder.dto.*;
import com.example.ApexMapFinder.service.AMFService;
import com.example.ApexMapFinder.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

//not @RestController b/c then getGreeting would return String "greeting"
@Controller
public class AMFController {

    @Autowired
    private AMFService amfService;
    @Autowired
    private NotificationService notificationSerivce;

    //Method to get current api making an api call n such
    @GetMapping("/amf")
    @ResponseBody
    public AMF amf(){
        AMF amf = amfService.getAMF();
        return amf;
    }

    //test for returning an html response
    @GetMapping("/")
    public String index(Model model) {
        AMF amfPOJO = amfService.getAMF();
        String currentBRImg = amfService.getMapImage("current", "battleRoyale");
        String nextBRImg = amfService.getMapImage("next", "battleRoyale");
        String currentArenaImg = amfPOJO.getArenas().getCurrent().getMap();
        String nextArenaImg = amfPOJO.getArenas().getNext().getMap();

        model.addAttribute(amfPOJO);
        model.addAttribute("current", currentBRImg);
        model.addAttribute("next", nextBRImg);
        model.addAttribute("currentArena", currentArenaImg);
        model.addAttribute("nextArena", nextArenaImg);

        return "greeting";
    }

    @GetMapping("/map_prototype")
    public String mapPrototype(Model model){
        AMF amf = amfService.getAMF();

        model.addAttribute("amf", amf);
        model.addAttribute("currentBR", amfService.getMapImage("current", "battleRoyale"));
        model.addAttribute("currentArena", amfService.getMapImage("current", "arenas"));
        model.addAttribute("currentRanked", amfService.getMapImage("current", "ranked"));
        model.addAttribute("currentArenaRanked", amfService.getMapImage("current", "arenaRanked"));
        model.addAttribute("arenasTimer", amfService.getEndTimer().get("arenas"));
        model.addAttribute("arenasRankedTimer", amfService.getEndTimer().get("arenasRanked"));
        model.addAttribute("battleRoyaleTimer", amfService.getEndTimer().get("battleRoyale"));

        return "mapPrototype";
    }
    @GetMapping("/current")
    public String currentMaps(Model model) {
        AMF amf = amfService.getAMF();
        model.addAttribute("amf", amf);

        model.addAttribute("currentBR", amfService.getMapImage("current", "battleRoyale"));
        model.addAttribute("currentArena", amfService.getMapImage("current", "arenas"));
        model.addAttribute("currentRanked", amfService.getMapImage("current", "ranked"));
        model.addAttribute("currentArenaRanked", amfService.getMapImage("current", "arenaRanked"));

        model.addAttribute("arenasTimer", amfService.getEndTimer().get("arenas"));
        model.addAttribute("arenasRankedTimer", amfService.getEndTimer().get("arenasRanked"));
        model.addAttribute("battleRoyaleTimer", amfService.getEndTimer().get("battleRoyale"));
        model.addAttribute("battleRoyaleRankedTimer", amfService.getEndTimer().get("battleRoyaleRanked"));

        return "currentMaps";
    }

    @GetMapping("/next")
    public String nextMaps(Model model) {
        AMF amf = amfService.getAMF();
        model.addAttribute("amf", amf);

        model.addAttribute("nextBR", amfService.getMapImage("next", "battleRoyale"));
        model.addAttribute("nextArena", amfService.getMapImage("next", "arenas"));
        model.addAttribute("nextRanked", amfService.getMapImage("next", "ranked"));
        model.addAttribute("nextArenaRanked", amfService.getMapImage("next", "arenaRanked"));

        model.addAttribute("arenasTimer", amfService.getEndTimer().get("arenas"));
        model.addAttribute("arenasRankedTimer", amfService.getEndTimer().get("arenasRanked"));
        model.addAttribute("battleRoyaleTimer", amfService.getEndTimer().get("battleRoyale"));
        model.addAttribute("battleRoyaleRankedTimer", amfService.getEndTimer().get("battleRoyaleRanked"));

        return "nextMaps";
    }
    //mapping for returning json with certain player data
    @GetMapping("/player")
    @ResponseBody
    public Player getPlayer(@RequestParam String name) {
        return amfService.getPlayer(name);
    }

    @GetMapping("/players")
    @ResponseBody
    public List<Player> getAllPlayers() {
        return amfService.getAllPlayers();
    }

//    @GetMapping("/registration")
//    public String showRegistrationForm(Model model){
//        Notification notification = new Notification();
//        notification.setGameMaps(new ArrayList<>());
//        model.addAttribute("notification", notification);
//        model.addAttribute("gameMap", new GameMap());
//        model.addAttribute("allMaps", MapEnum.values());
//        model.addAttribute("arenaMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS));
//        model.addAttribute("battleRoyaleMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE));
//        model.addAttribute("gamemodes", GamemodeEnum.values());
//        return "registration";
//    }

    @GetMapping("/notificationSignUp")
    public String notificationSignUp(Model model){
        Notification notification = new Notification();
        model.addAttribute("notification", notification);
        model.addAttribute("arenaRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS_RANKED));
        model.addAttribute("arenaMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS));
        model.addAttribute("battleRoyaleRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE_RANKED));
        model.addAttribute("battleRoyaleMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE));
        return "notificationSignup";
    }
    @PostMapping("/saveNotification")
    public String saveNotification(@ModelAttribute("notification") Notification notification){
        System.out.println(notification.toString());
        notificationSerivce.saveNotification(notification);
        System.out.println(notificationSerivce.getNotification(notification.getEmail()));
        return "confirmation";
    }

    //OG notification endpoint
//    @GetMapping("/signup")
//    public String signup(){
//
//        return "signUp";
//    }
//    @GetMapping("/save")
//    public String saveUser(@RequestParam MultiValueMap<String, String> allParams /*@RequestParam(value = "email", required = true) String email, @RequestParam(value = "br")List<String> battleRoyaleMaps), @RequestParam(value = "ar")List<String> arenas*/){
//        //Problem: when page reloads existing data is inserted b/c still in url
//        System.out.println(allParams.get("email").toString());
//        System.out.println(allParams.get("br").toString());
//        System.out.println(allParams.get("ar").toString());
//
//        List<String> maps = new ArrayList<>();
//        for(String map : allParams.get("br")){
//            maps.add("br_" + map);
//        }
//        for(String map : allParams.get("ar")){
//            maps.add("ar_" + map);
//        }
//        Notification notification = new Notification();
//        notification.setEmail(allParams.get("email").get(0));
//        notification.setMaps(maps);
//
//        notificationSerivce.save(notification);
//        System.out.println(notification.toString());
////        notificationSerivce.saveNotification(notification);
//        return "confirmation";
//    }
}
