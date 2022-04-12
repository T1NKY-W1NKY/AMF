package com.example.demo.controller;

import com.example.demo.dto.AMF;
import com.example.demo.dto.Player;
import com.example.demo.service.AMFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

//not @RestController b/c then getGreeting would return String "greeting"
@Controller
public class AMFController {

    @Autowired
    private AMFService amfService;

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

    @GetMapping("/test")
    public String test(Model model) throws ParseException {
        AMF amf = amfService.getAMF();
        List<Long> mapTimes = amfService.getMapTimes();

        model.addAttribute("amf", amf);
        model.addAttribute("currentBR", amfService.getMapImage("current", "battleRoyale"));
        model.addAttribute("nextBR", amfService.getMapImage("next", "battleRoyale"));
        model.addAttribute("currentArena", amfService.getMapImage("current", "arenas"));
        model.addAttribute("nextArena", amfService.getMapImage("next", "arenas"));
        model.addAttribute("currentRanked", amfService.getMapImage("current", "ranked"));
        model.addAttribute("currentArenaRanked", amfService.getMapImage("current", "arenaRanked"));
        model.addAttribute("nextArenaRanked", amfService.getMapImage("next", "arenaRanked"));
//        model.addAttribute("arenasTimer", mapTimes.get(0));
//        model.addAttribute("arenasRankedTimer", mapTimes.get(1));
//        model.addAttribute("battleRoyaleTimer", mapTimes.get(2));
        model.addAttribute("arenasTimer", amfService.getEndTimer().get("arenas"));
        model.addAttribute("arenasRankedTimer", amfService.getEndTimer().get("arenasRanked"));
        model.addAttribute("battleRoyaleTimer", amfService.getEndTimer().get("battleRoyale"));

        return "bootstartjs";
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

}
