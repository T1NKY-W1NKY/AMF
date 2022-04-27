package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Gamemode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JoinColumn
    @OneToOne(cascade = {CascadeType.ALL})
    private State current;
    @OneToOne(cascade = {CascadeType.ALL})
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
