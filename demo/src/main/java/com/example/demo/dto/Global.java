package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)

public @Data
class Global {
    private String name;
    private String platform;
    private int level;
}
