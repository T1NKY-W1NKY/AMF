package com.example.ApexMapFinder.dao;

import com.example.ApexMapFinder.dto.Notification;
import com.example.ApexMapFinder.dto.Notification;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    Notification findByEmail(String email);

    boolean existsByEmail(String email);

    Notification findById(int id);


}
