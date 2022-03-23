package com.example.demo.dao;

import com.example.demo.dto.Global;
import com.example.demo.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerDAO {

    @Autowired
    PlayerRepository playerRepository;

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
        if(findByPlayerName(player.getGlobal().getName()) == null) {
            return playerRepository.save(player);
        }
        //TODO: return error message if player already exists
        return null;
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
        return player;
    }
    public Player updatePlayer(String name, Player updatedPlayer){
        Player player = findByPlayerName(name);
        Global global = player.getGlobal();
        Global updatedGlobal = updatedPlayer.getGlobal();
        global.setLevel(updatedGlobal.getLevel());
        global.setName(updatedGlobal.getName());
        global.setPlatform(updatedGlobal.getPlatform());
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


