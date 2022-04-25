package com.example.demo.dao;

import com.example.demo.dto.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
}
