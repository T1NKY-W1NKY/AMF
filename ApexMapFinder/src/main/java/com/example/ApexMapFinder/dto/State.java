package com.example.ApexMapFinder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Needed or else error will be thrown for undefined variables (side note: can't be defined in higher up class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String map;
    private String remainingTimer;

    public State() {
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getRemainingTimer() {
        return remainingTimer;
    }

    public void setRemainingTimer(String remainingTimer) {
        this.remainingTimer = remainingTimer;
    }

    @Override
    public String toString() {
        return "State{" +
                "map='" + map + '\'' +
                ", remainingTimer='" + remainingTimer + '\'' +
                '}';
    }
}
