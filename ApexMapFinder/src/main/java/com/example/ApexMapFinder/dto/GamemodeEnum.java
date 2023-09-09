package com.example.ApexMapFinder.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GamemodeEnum {

    BATTLEROYALE ("battleRoyale"),
    ARENAS ("arenas"),
    BATTLEROYALE_RANKED ("battleRoyaleRanked"),
    ARENAS_RANKED ("arenasRanked");

    private String name;

    GamemodeEnum(String name){
        this.name = name;
    }

    private static final Map<String, GamemodeEnum> map;
    static{
        map = new HashMap<String, GamemodeEnum>();
        for (GamemodeEnum v : GamemodeEnum.values()) {
            map.put(v.name, v);
        }
    }

    public static GamemodeEnum findByName(String name){
        return map.get(name);
    }
}
