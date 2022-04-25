package com.example.demo.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String email;
    Map<String, List<String>> mapList;
    // Do not confuscate Map data type and apex legend maps :()
    /* How mapList will function
    BR           - Map List
    Arena        - Map List
    Ranked BR    - Map List
    Ranked Arena - Map List
     */
}
