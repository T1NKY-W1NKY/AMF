package com.example.ApexMapFinder.dao;

import com.example.ApexMapFinder.dto.Global;
import com.example.ApexMapFinder.dto.Player;
import com.example.ApexMapFinder.other.DynamicSchedulingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerDAO {

    @Autowired
    PlayerRepository playerRepository;

    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulingConfig.class);

    //Steam invalidates this method by allowing duplicate names
    public Player findByPlayerName(String name){

        Iterable<Player> players = playerRepository.findAll();
        for(Player player : players){
            if(player.getGlobal().getName().equalsIgnoreCase(name)){
                return player;
            }
        }

        //TODO: return player not found
        return null;
    }

    public Player findByPlayerUid(Long uid){

        Iterable<Player> players = playerRepository.findAll();
        for(Player player : players){
            if(player.getGlobal().getUid() == uid){
                return player;
            }
        }

        //TODO: return player not found
        return null;
    }

    public Player findByPlayerId(int id){
        Iterable<Player> players = playerRepository.findAll();
        for(Player player : players){
            if(player.getPlayerId() == id){
                return player;
            }
        }
        //TODO: return player not found
        return null;
    }

    public Player savePlayer(Player player){
        if(findByPlayerUid(player.getGlobal().getUid()) == null) {
            log.info("Saving player: " + player.getGlobal().getName());
            return playerRepository.save(player);
        }
        //TODO: return error message if player already exists, currently is returning existing player because-
        log.info("Player already exists");
        return findByPlayerName(player.getGlobal().getName());
    }

    //TODO: add functionality for either a player id or name to update given player with new one
    @Transactional
    public Player updatePlayer(int id, Player updatedPlayer){
        Player player = playerRepository.findById(id).get();
        Global global = player.getGlobal();
        Global updatedGlobal = updatedPlayer.getGlobal();
        global.setLevel(updatedGlobal.getLevel());
        global.setName(updatedGlobal.getName());
        global.setPlatform(updatedGlobal.getPlatform());
        global.setToNextLevelPercent(updatedGlobal.getToNextLevelPercent());
        return player;
    }

    //by name
    @Transactional
    public Player updatePlayer(String name, Player updatedPlayer){
        Player player = findByPlayerName(name);
        Global global = player.getGlobal();
        Global updatedGlobal = updatedPlayer.getGlobal();
        global.setLevel(updatedGlobal.getLevel());
        global.setName(updatedGlobal.getName());
        global.setPlatform(updatedGlobal.getPlatform());
        global.setToNextLevelPercent(updatedGlobal.getToNextLevelPercent());

        return player;
    }

    public List<Player> getAllPlayers(){
        List<Player> allPlayers = new ArrayList<>();
        Iterable<Player> players = playerRepository.findAll();
        for(Player player : players){
                allPlayers.add(player);
            }
        return allPlayers;
        }
    }


