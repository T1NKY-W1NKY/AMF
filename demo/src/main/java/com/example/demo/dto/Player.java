package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public @Data
class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer playerId;
    @OneToOne(cascade = {CascadeType.ALL})
    private Global global;

}