package com.example.demo.dao;

import com.example.demo.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class PlayerDAO {

    @Autowired
    PlayerRepository playerRepository;

    public Player getPlayerByID(int id){
        return playerRepository.findById(id).get();
    }

    public Player savePlayer(Player player){
        return playerRepository.save(player);
    }

//    //add functionality for either a player id or name to update given player with new one or something
//    @Transactional
//    public Player updatePlayer(Player player){
//        return playerRepository.(player).get();
//    }
}
