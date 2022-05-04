package com.example.demo.dao;

import com.example.demo.dto.Notification;
import com.example.demo.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationDAO {

    @Autowired
    NotificationRepository notificationRepository;

    public Notification getNotificationByEmail(String email){
        return notificationRepository.findByEmail(email);
    }

    public List<Notification> getAllNotifications(){
        List<Notification> notifications = new ArrayList<>();
        Iterable<Notification> allNotification = notificationRepository.findAll();
        for(Notification notification : allNotification){
            notifications.add(notification);
        }
        return notifications;
    }

    public void save(Notification notification){
        notificationRepository.save(notification);
    }

    public void delete(String email){
        notificationRepository.delete(getNotificationByEmail(email));
    }

    public boolean userExists(String email){
        return notificationRepository.existsByEmail(email);
    }

}

