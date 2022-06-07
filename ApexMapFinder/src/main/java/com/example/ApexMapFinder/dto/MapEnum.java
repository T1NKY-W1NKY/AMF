package com.example.ApexMapFinder.dto;

public enum MapEnum {

    DROPOFF (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED),
    ENCORE (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED),
    HABITA (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED),
    OVERFLOW (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED),
    PARTY_CRASHER (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED),
    PHASE_RUNNER (GamemodeEnum.ARENAS, GamemodeEnum.ARENAS_RANKED),
    STORM_POINT (GamemodeEnum.BATTLEROYALE, GamemodeEnum.BATTLEROYALE_RANKED),
    WORLDS_EDGE (GamemodeEnum.BATTLEROYALE, GamemodeEnum.BATTLEROYALE_RANKED),
    KINGS_CANYON (GamemodeEnum.BATTLEROYALE, GamemodeEnum.BATTLEROYALE_RANKED),
    OLYMPUS (GamemodeEnum.BATTLEROYALE, GamemodeEnum.BATTLEROYALE_RANKED);

    private GamemodeEnum casual;
    private GamemodeEnum ranked;

    MapEnum(GamemodeEnum casual, GamemodeEnum ranked) {
        this.casual = casual;
        this.ranked = ranked;
    }
}
