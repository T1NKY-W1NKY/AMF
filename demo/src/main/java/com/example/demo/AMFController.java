package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//not @RestController b/c then getGreeting would return String "greeting"
@Controller
public class AMFController {

    private final AMFService amfService;

    @Autowired
    public AMFController(AMFService amfService){this.amfService = amfService;}

    //Method to get current api making an api call n such
    @GetMapping("/amf")
    @ResponseBody
    public AMF amf(){

        return amfService.getCurrentAMF();
    }

    //test for returning an html response
    @GetMapping("/greeting")
    public String getGreeting(Model model) {
        AMF amfPOJO = amfService.getCurrentAMF();
        String currentArenaImg = amfService.getMapImage();
        model.addAttribute(amfPOJO);
        model.addAttribute(currentArenaImg);
        return "greeting";
    }

    /* TODO:
    -look into using @RequestParam more, how does flow of command work n stuff idk
    -look into using html for displaying images and live update page??
    */
    @GetMapping("/player")
    public String getPlayer(@RequestParam String id, Model model) {
        //receive input from endpoint then display json response from apex api
        //later on can go to service put json into POJO then display specific attributes
        return "player";
    }


}
