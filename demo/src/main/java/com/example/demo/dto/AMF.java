package com.example.demo.dto;

import com.example.demo.dto.Gamemode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
//added jsonignore because it was saying there was a 'current' field obstructing mapping, could be the id value acting up
@Entity
//AMF (ApexMapFinder)
public class AMF {

    //created id value for mysql to generate
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer amfId;
    @JsonProperty("battle_royale")
    @OneToOne(cascade = {CascadeType.ALL})
    private Gamemode battleRoyale;
    @OneToOne(cascade = {CascadeType.ALL})
    private Gamemode arenas;
    @OneToOne(cascade = {CascadeType.ALL})
    private Gamemode ranked;
    @OneToOne(cascade = {CascadeType.ALL})
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
