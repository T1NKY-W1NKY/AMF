package com.example.ApexMapFinder.service;

import com.example.ApexMapFinder.dao.NotificationDAO;
import com.example.ApexMapFinder.dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;

    public void save(Notification notification) {
        notificationDAO.save(notification);
    }

    public List<Notification> getAllNotifications(){
        return notificationDAO.getAllNotifications();
    }

    public Notification getNotification(String email){
        return notificationDAO.getNotificationByEmail(email);
    }

    public void updateNotification(Notification newNotification){
    }

    public void deleteNotification(String email){
    }
}
