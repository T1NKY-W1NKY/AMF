package com.example.demo.dto;

import com.example.demo.dto.Gamemode;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
//AMF (ApexMapFinder)
public class AMF {

    //created id value for mysql to generate
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer amfId;
    @JsonProperty("battle_royale")
    @OneToOne
    private Gamemode battleRoyale;
    @OneToOne
    private Gamemode arenas;
    @OneToOne
    private Gamemode ranked;
    @OneToOne
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
