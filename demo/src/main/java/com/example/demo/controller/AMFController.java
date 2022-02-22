package com.example.demo.controller;

import com.example.demo.dto.AMF;
import com.example.demo.dto.Player;
import com.example.demo.service.AMFService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//not @RestController b/c then getGreeting would return String "greeting"
@Controller
public class AMFController {

    @Autowired
    private AMFService amfService;

    //Method to get current api making an api call n such
    @GetMapping("/amf")
    @ResponseBody
    public AMF amf(){
        return amfService.getAmf();
    }

    //test for returning an html response
    @GetMapping("/")
    public String index(Model model) {
        AMF amfPOJO = amfService.getAmf();
        String currentBRImg = amfService.getMapImage("current");
        String nextBRImg = amfService.getMapImage("next");
        String currentArenaImg = amfPOJO.getArenas().getCurrent().getMap();
        String nextArenaImg = amfPOJO.getArenas().getNext().getMap();
        model.addAttribute(amfPOJO);
        model.addAttribute("current", currentBRImg);
        model.addAttribute("next", nextBRImg);
        model.addAttribute("currentArena", currentArenaImg);
        model.addAttribute("nextArena", nextArenaImg);

        return "greeting";
    }

    //mapping for returning json with certain player data
    @GetMapping("/player")
    @ResponseBody
    public Player getPlayer(@RequestParam String name) {
        Player player = amfService.getPlayer(name);
        return player;
    }


}
