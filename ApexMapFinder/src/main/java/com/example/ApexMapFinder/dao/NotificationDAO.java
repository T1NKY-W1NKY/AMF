package com.example.ApexMapFinder.dao;

import com.example.ApexMapFinder.dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationDAO {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    VerificationTokenDAO verificationTokenDAO;

    public Notification getNotificationByEmail(String email){
        return notificationRepository.findByEmail(email);
    }

    public Notification getNotificationById(int id){return notificationRepository.findById(id);
    }

    public List<Notification> getAllNotifications(){
        List<Notification> notifications = new ArrayList<>();
        Iterable<Notification> allNotification = notificationRepository.findAll();
        for(Notification notification : allNotification){
            notifications.add(notification);
        }
        return notifications;
    }

    public Notification save(Notification notification){
        return notificationRepository.save(notification);
    }

    public void delete(String email){
//        verificationTokenDAO.delete(getNotificationByEmail(email).getId());
        notificationRepository.delete(getNotificationByEmail(email));
    }

    public boolean userExists(String email){
        return notificationRepository.existsByEmail(email);
    }

    public List<Notification> getUnverifiedNotifications(){
        List<Notification> unverifiedNotifications = new ArrayList<>();
        Iterable<Notification> notifications = notificationRepository.findAll();
        for(Notification notification : notifications){
            if(!notification.getEnabled()){
                unverifiedNotifications.add(notification);
            }
        }
        return unverifiedNotifications;

    }
}

