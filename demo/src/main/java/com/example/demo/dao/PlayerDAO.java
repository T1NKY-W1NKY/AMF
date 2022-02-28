package com.example.demo.dao;

import com.example.demo.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

//    //add functionality for either a player id or name to update given player with new one or something
//    @Transactional
//    public Player updatePlayer(Player player){
//        return playerRepository.(player).get();
//    }

    public List<Player> getAllPlayers(){
        List<Player> allPlayers = new ArrayList<>();
        Iterable<Player> players = playerRepository.findAll();
        for(Player player : players){
                allPlayers.add(player);
            }
        return allPlayers;
        }
    }


