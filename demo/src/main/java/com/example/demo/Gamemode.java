package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Gamemode {

    private State current;
    private State next;


    public Gamemode() {
    }

    public State getCurrent() {
        return current;
    }

    public void setCurrent(State current) {
        this.current = current;
    }

    public State getNext() {
        return next;
    }

    public void setNext(State next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Gamemode{" +
                "current=" + current +
                ", next=" + next +
                '}';
    }
}
