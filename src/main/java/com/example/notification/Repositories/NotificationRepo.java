package com.example.notification.Repositories;


import com.example.notification.models.Notification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepo extends CrudRepository<Notification ,Integer> , JpaSpecificationExecutor <Notification> {

    Notification findById(int id);
    List<Notification> findAllByMessageAndSendingState(String message , String state);

}

