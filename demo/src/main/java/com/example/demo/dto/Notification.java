package com.example.demo.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String email;
//    @ElementCollection
//    List<String> gamemodes;

    //each map with start with the gamemode they are attributed to:
    //BR_KingsCanyon | A_DropOff | RBR_Olympus | RA_PhaseRunner
    @ElementCollection
    List<String> maps;
//    Map<String, List<String>> mapList;
//    @OneToMany
//    Map<String, String> mapList;

    /* How mapList will function
    BR           - Map List
    Arena        - Map List
    Ranked BR    - Map List
    Ranked Arena - Map List
     */
}
