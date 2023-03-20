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
    RANKED_BROKEN_MOON (GamemodeEnum.BATTLEROYALE_RANKED, "Broken Moon"),

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
    CASUAL_OLYMPUS (GamemodeEnum.BATTLEROYALE, "Olympus"),
    CASUAL_BROKEN_MOON (GamemodeEnum.BATTLEROYALE, "Broken Moon");

//  Developed when working on an enum coverter class, may be revisited... Baeldung Link: https://www.baeldung.com/jpa-persisting-enums-in-jpa
//    RANKED_DROPOFF (GamemodeEnum.ARENAS_RANKED, "Drop Off", "RDO"),
//    RANKED_ENCORE (GamemodeEnum.ARENAS_RANKED, "Encore", "REC"),
//    //Habitat is named "Habitat 4" officially
//    RANKED_HABITAT (GamemodeEnum.ARENAS_RANKED, "Habitat", "RHT"),
//    RANKED_OVERFLOW (GamemodeEnum.ARENAS_RANKED, "Overflow", "ROF"),
//    RANKED_PARTY_CRASHER (GamemodeEnum.ARENAS_RANKED,"Party Crasher", "RPC"),
//    RANKED_PHASE_RUNNER (GamemodeEnum.ARENAS_RANKED, "Phase Runner", "RPR"),
//    RANKED_STORM_POINT (GamemodeEnum.BATTLEROYALE_RANKED, "Storm Point", "RSP"),
//    RANKED_WORLDS_EDGE (GamemodeEnum.BATTLEROYALE_RANKED, "World's Edge", "RWE"),
//    RANKED_KINGS_CANYON (GamemodeEnum.BATTLEROYALE_RANKED, "Kings Canyon", "RKC"),
//    RANKED_OLYMPUS (GamemodeEnum.BATTLEROYALE_RANKED, "Olympus", "ROP"),
//    CASUAL_DROPOFF (GamemodeEnum.ARENAS, "Drop Off", "CDO"),
//    CASUAL_ENCORE (GamemodeEnum.ARENAS, "Encore", "CEC"),
//    //Habitat is named "Habitat 4" officially
//    CASUAL_HABITAT (GamemodeEnum.ARENAS, "Habitat", "CHT"),
//    CASUAL_OVERFLOW (GamemodeEnum.ARENAS, "Overflow", "COF"),
//    CASUAL_PARTY_CRASHER (GamemodeEnum.ARENAS,"Party Crasher", "CPC"),
//    CASUAL_PHASE_RUNNER (GamemodeEnum.ARENAS, "Phase Runner", "CPR"),
//    CASUAL_STORM_POINT (GamemodeEnum.BATTLEROYALE, "Storm Point", "CSP"),
//    CASUAL_WORLDS_EDGE (GamemodeEnum.BATTLEROYALE, "World's Edge", "CWE"),
//    CASUAL_KINGS_CANYON (GamemodeEnum.BATTLEROYALE, "Kings Canyon", "CKC"),
//    CASUAL_OLYMPUS (GamemodeEnum.BATTLEROYALE, "Olympus", "COP");


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
