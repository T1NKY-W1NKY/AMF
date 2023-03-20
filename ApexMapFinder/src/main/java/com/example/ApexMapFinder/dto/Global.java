package com.example.ApexMapFinder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public @Data
class Global {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;
    private String name;
    private String originName;
    private String platform;
    private int level;
    private int toNextLevelPercent;
}
