package com.example.ApexMapFinder.controller;

import com.example.ApexMapFinder.dto.*;
import com.example.ApexMapFinder.other.DynamicSchedulingConfig;
import com.example.ApexMapFinder.service.AMFService;
import com.example.ApexMapFinder.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//not @RestController b/c then getGreeting would return String "greeting"
@Controller
public class AMFController {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private AMFService amfService;
    @Autowired
    private NotificationService notificationService;
    Notification notification = new Notification();

    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulingConfig.class);


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
//        AMF amfPOJO = amfService.getAMF();
//        String currentBRImg = amfService.getMapImage("current", "battleRoyale");
//        String nextBRImg = amfService.getMapImage("next", "battleRoyale");
//        String currentArenaImg = amfPOJO.getArenas().getCurrent().getMap();
//        String nextArenaImg = amfPOJO.getArenas().getNext().getMap();
//
//        model.addAttribute(amfPOJO);
//        model.addAttribute("current", currentBRImg);
//        model.addAttribute("next", nextBRImg);
//        model.addAttribute("currentArena", currentArenaImg);
//        model.addAttribute("nextArena", nextArenaImg);

        return "homePage";
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
        if(amf == null){
            log.info("null amf");
            amf = amfService.updateAMF();
        }
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
        if(amf == null){
            log.info("null amf");
            amf = amfService.updateAMF();
        }
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
//    @ResponseBody
    public String getPlayer(@RequestParam String name, Model model) {
        Player player = amfService.getPlayerByName(name);
        if(player.getGlobal() == null)
        {
            return "playerNotFound";
        }
        model.addAttribute("player", player);
            return "player";
    }

    @GetMapping("/players")
    @ResponseBody
    public List<Player> getAllPlayers() {
        return amfService.getAllPlayers();
    }

    @GetMapping("/notificationSignUp")
    public String notificationSignUp(Model model){
        model.addAttribute("notification", notification);
        model.addAttribute("arenaRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS_RANKED));
        model.addAttribute("arenaMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS));
        model.addAttribute("battleRoyaleRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE_RANKED));
        model.addAttribute("battleRoyaleMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE));
        return "notificationSignUp";
    }

    @PostMapping("/deleteNotification")
    public String deleteNotificationByEmail(@RequestParam String email, Model model){

        boolean emailExists = notificationService.emailExists(email);
        log.info("Deleting notifcations for: " + email + " | " + emailExists);
        model.addAttribute("notification", notification);
        model.addAttribute("arenaRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS_RANKED));
        model.addAttribute("arenaMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS));
        model.addAttribute("battleRoyaleRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE_RANKED));
        model.addAttribute("battleRoyaleMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE));
        try{
            notificationService.deleteNotification(email);
        }
        catch (Exception e){
            model.addAttribute("emailNotExists", !emailExists);
            return "notificationSignUp";
        }

        model.addAttribute("deleteSuccess", true);
        return "notificationSignUp";
    }

    @GetMapping("/notificationDeletePage")
    public String notificationDeletePage(){
        return "notificationDelete";
    }

    @PostMapping("/saveNotification")
    public String saveNotification(@Valid @ModelAttribute("notification") Notification notification, BindingResult bindingResult, Model model){
        boolean emailExists = notificationService.emailExists(notification.getEmail());
        model.addAttribute("notification", notification);
        model.addAttribute("arenaRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS_RANKED));
        model.addAttribute("arenaMaps", MapEnum.getGamemodeMaps(GamemodeEnum.ARENAS));
        model.addAttribute("battleRoyaleRankedMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE_RANKED));
        model.addAttribute("battleRoyaleMaps", MapEnum.getGamemodeMaps(GamemodeEnum.BATTLEROYALE));

        if (bindingResult.hasErrors() || emailExists){
            model.addAttribute("emailExists", emailExists);
            return "notificationSignUp";
        }

        //I couldn't give a clear answer why I have this try catch here and the if statement above to catch any problems..
        //I do not 100% understand what I am doing.. following this guide: https://www.baeldung.com/registration-verify-user-by-email#1-using-a-spring-event-to-create-the-token-and-send-the-verification-email
        try {
            System.out.println(notification.toString());
            notificationService.saveNotification(notification);
            System.out.println(notificationService.getNotification(notification.getEmail()));
        }
        //catch kindve redundant to if statement above.
        //adding because it is part of the guide i'm following
        catch (Exception e){
            model.addAttribute("emailExists", emailExists);
            return "notificationSignUp";
        }


        model.addAttribute("signupSuccess", true);
        return "notificationSignUp";
    }

    @GetMapping("/registrationConfirmation")
    public String confirmRegistration(@RequestParam String token, Model model){

        //Fix redirects if something went wrong
        String message = "error";

        VerificationToken verificationToken = notificationService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", message);
            return "redirect:/badUser.html";
        }

        Notification notification = verificationToken.getNotification();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", message);
            return "redirect:/badUser.html";
        }

        notification.setEnabled(true);
        //really updating notification now that they are confirmed
        notificationService.saveNotification(notification);
        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmation(Model model){

        model.addAttribute("confirmationMessage", "-email successfully validated-");
        return "confirmation";
    }

}
