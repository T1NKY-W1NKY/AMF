package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//AMF (ApexMapFinder)
public class AMF {

    @JsonProperty("battle_royale")
    private Gamemode battleRoyale;
    private Gamemode arenas;
    private Gamemode ranked;
    private Gamemode arenasRanked;

    public Gamemode getRanked() {
        return ranked;
    }

    public void setRanked(Gamemode ranked) {
        this.ranked = ranked;
    }

    public Gamemode getArenasRanked() {
        return arenasRanked;
    }

    public void setArenasRanked(Gamemode arenasRanked) {
        this.arenasRanked = arenasRanked;
    }

    public AMF() {
    }

    public Gamemode getBattleRoyale() {
        return battleRoyale;
    }

    public void setBattleRoyale(Gamemode battle_royale) {
        this.battleRoyale = battle_royale;
    }

    public Gamemode getArenas() {
        return arenas;
    }

    public void setArenas(Gamemode arenas) {
        this.arenas = arenas;
    }

    @Override
    public String toString() {
        return "AMF{" +
                "battleRoyale=" + battleRoyale +
                ", arenas=" + arenas +
                ", ranked=" + ranked +
                ", arenasRanked=" + arenasRanked +
                '}';
    }
}
