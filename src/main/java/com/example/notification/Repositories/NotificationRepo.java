package com.example.notification.Repositories;


import com.example.notification.models.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepo extends CrudRepository<Notification ,Integer> {

    Notification findById(int id);
}

