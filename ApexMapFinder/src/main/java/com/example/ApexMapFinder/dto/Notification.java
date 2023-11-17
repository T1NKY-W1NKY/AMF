package com.example.ApexMapFinder.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    //makes email need to be unique, not best solution; i dont think
    @Column(unique = true)
    @NotBlank
//    @Email
    String email;

    Boolean isValid;

//    @Enumerated
//    @ElementCollection(targetClass = MapEnum.class)
//    @CollectionTable
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gameMaps", joinColumns = @JoinColumn(name = "notification_id"))
    @Enumerated(EnumType.STRING)
    @NotEmpty
    List<MapEnum> gameMaps;
//    @ElementCollection
//    List<GameMap> gameMaps;

    public Notification(){

    }
//    @ElementCollection
//    List<String> gamemodes;

    //each map with start with the gamemode they are attributed to:
    //BR_KingsCanyon | A_DropOff | RBR_Olympus | RA_PhaseRunner
//    @NotNull
//    @ElementCollection
//    List<String> maps;
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
