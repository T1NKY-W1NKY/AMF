package com.example.demo.dao;

import com.example.demo.dto.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {}
