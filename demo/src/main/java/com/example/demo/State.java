package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

//Needed or else error will be thrown for undefined variables (side note: can't be defined in higher up class)
@JsonIgnoreProperties(ignoreUnknown = true)

public class State {

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
