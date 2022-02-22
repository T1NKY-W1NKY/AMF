package com.example.demo.dto;

import javax.persistence.*;

@Entity
public class Gamemode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne
    private State current;
    @OneToOne
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
