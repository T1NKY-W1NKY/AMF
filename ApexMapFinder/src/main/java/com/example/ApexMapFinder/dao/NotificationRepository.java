package com.example.ApexMapFinder.dao;

import com.example.ApexMapFinder.dto.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    Notification findByEmail(String email);

    boolean existsByEmail(String email);

}
