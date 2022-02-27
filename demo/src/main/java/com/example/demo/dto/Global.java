package com.example.demo.dto;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer globalId;
    private String name;
    private String platform;
    private int level;
}
