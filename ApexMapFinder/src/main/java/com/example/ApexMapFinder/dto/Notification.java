package com.example.ApexMapFinder.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotNull
    String email;
//    @ElementCollection
//    List<String> gamemodes;

    //each map with start with the gamemode they are attributed to:
    //BR_KingsCanyon | A_DropOff | RBR_Olympus | RA_PhaseRunner
    @NotNull
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
