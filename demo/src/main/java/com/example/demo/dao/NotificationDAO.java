package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationDAO {

    @Autowired
    NotificationRepository notificationRepository;
}
