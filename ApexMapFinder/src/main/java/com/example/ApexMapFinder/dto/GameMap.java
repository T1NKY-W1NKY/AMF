package com.example.ApexMapFinder.dto;


import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public @Data class GameMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    GamemodeEnum gamemode;
    MapEnum map;

}
