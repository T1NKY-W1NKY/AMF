package com.example.ApexMapFinder.dto;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public @Data class GameMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    GamemodeEnum gamemode;
    MapEnum map;
}
