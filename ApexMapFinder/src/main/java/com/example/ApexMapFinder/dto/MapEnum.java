package com.example.ApexMapFinder.dto;

import java.util.ArrayList;
import java.util.List;

public enum MapEnum {

    RANKED_DROPOFF (GamemodeEnum.ARENAS_RANKED, "Drop Off"),
    RANKED_ENCORE (GamemodeEnum.ARENAS_RANKED, "Encore"),
    //Habitat is named "Habitat 4" officially
    RANKED_HABITAT (GamemodeEnum.ARENAS_RANKED, "Habitat"),
    RANKED_OVERFLOW (GamemodeEnum.ARENAS_RANKED, "Overflow"),
    RANKED_PARTY_CRASHER (GamemodeEnum.ARENAS_RANKED,"Party Crasher"),
    RANKED_PHASE_RUNNER (GamemodeEnum.ARENAS_RANKED, "Phase Runner"),
    RANKED_STORM_POINT (GamemodeEnum.BATTLEROYALE_RANKED, "Storm Point"),
    RANKED_WORLDS_EDGE (GamemodeEnum.BATTLEROYALE_RANKED, "World's Edge"),
    RANKED_KINGS_CANYON (GamemodeEnum.BATTLEROYALE_RANKED, "Kings Canyon"),
    RANKED_OLYMPUS (GamemodeEnum.BATTLEROYALE_RANKED, "Olympus"),
    CASUAL_DROPOFF (GamemodeEnum.ARENAS, "Drop Off"),
    CASUAL_ENCORE (GamemodeEnum.ARENAS, "Encore"),
    //Habitat is named "Habitat 4" officially
    CASUAL_HABITAT (GamemodeEnum.ARENAS, "Habitat"),
    CASUAL_OVERFLOW (GamemodeEnum.ARENAS, "Overflow"),
    CASUAL_PARTY_CRASHER (GamemodeEnum.ARENAS,"Party Crasher"),
    CASUAL_PHASE_RUNNER (GamemodeEnum.ARENAS, "Phase Runner"),
    CASUAL_STORM_POINT (GamemodeEnum.BATTLEROYALE, "Storm Point"),
    CASUAL_WORLDS_EDGE (GamemodeEnum.BATTLEROYALE, "World's Edge"),
    CASUAL_KINGS_CANYON (GamemodeEnum.BATTLEROYALE, "Kings Canyon"),
    CASUAL_OLYMPUS (GamemodeEnum.BATTLEROYALE, "Olympus");


    private GamemodeEnum gamemode;
    private String name;

    MapEnum(GamemodeEnum gamemode, String name) {
        this.gamemode = gamemode;
        this.name = name;
    }

    public static List<MapEnum> getGamemodeMaps(GamemodeEnum gamemodeEnum){
        List<MapEnum> maps = new ArrayList<>();
        for(MapEnum map : MapEnum.values()){
            if(map.gamemode == gamemodeEnum){
                maps.add(map);
            }
        }
        return maps;
    }

    public String getName(){
        return name;
    }

    public GamemodeEnum getGamemode(){
        return gamemode;
    }
}
