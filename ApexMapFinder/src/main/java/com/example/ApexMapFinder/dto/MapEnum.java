package com.example.ApexMapFinder.dto;

import java.util.ArrayList;
import java.util.List;

public enum MapEnum {

    DROPOFF (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED, "Drop Off"),
    ENCORE (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED, "Encore"),
    //Habitat is named "Habitat 4" officially
    HABITAT (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED, "Habitat"),
    OVERFLOW (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED, "Overflow"),
    PARTY_CRASHER (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED,"Party Crasher"),
    PHASE_RUNNER (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED, "Phase Runner"),
    STORM_POINT (GamemodeEnum.BATTLEROYALE, GamemodeEnum.BATTLEROYALE_RANKED, "Storm Point"),
    WORLDS_EDGE (GamemodeEnum.BATTLEROYALE, GamemodeEnum.BATTLEROYALE_RANKED, "World's Edge"),
    KINGS_CANYON (GamemodeEnum.BATTLEROYALE, GamemodeEnum.BATTLEROYALE_RANKED, "Kings Canyon"),
    OLYMPUS (GamemodeEnum.BATTLEROYALE, GamemodeEnum.BATTLEROYALE_RANKED, "Olympus");

    private GamemodeEnum casual;
    private GamemodeEnum ranked;

    private String name;

    MapEnum(GamemodeEnum casual, GamemodeEnum ranked, String name) {
        this.casual = casual;
        this.ranked = ranked;
        this.name = name;
    }

    public static List<MapEnum> getGamemodeMaps(GamemodeEnum gamemodeEnum){
        List<MapEnum> maps = new ArrayList<>();
        for(MapEnum map : MapEnum.values()){
            if(map.casual == gamemodeEnum || map.ranked == gamemodeEnum){
                maps.add(map);
            }
        }
        return maps;
    }

    public String getName(){
        return name;
    }

    public GamemodeEnum getCasual(){
        return casual;
    }

    public GamemodeEnum getRanked(){
        return ranked;
    }
}
