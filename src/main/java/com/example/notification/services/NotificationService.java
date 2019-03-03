package com.example.notification.services;


import com.example.notification.Repositories.NotificationRepo;
import com.example.notification.models.Notification;
import com.example.notification.models.NotificationStructure;
import com.example.notification.models.Receiver;
import com.example.notification.specifications.NotificationSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;




    public void sendAndSaveNotification(NotificationStructure notificationStructure){

        for(Receiver  receiver : notificationStructure.getReceivers())
        {
            Notification notification = new Notification();
            notification.setMessage(notificationStructure.getMessage());
            notification.setReceiver(receiver.getUsername());
            notification.setSender(notificationStructure.getFrom());
            notification.setSendingState("pending");

            // save notification in notification log
            notificationRepo.save(notification);

            // send notification to user
            messagingTemplate.convertAndSendToUser(receiver.getUsername(),"/queue/notify",notification);
        }

    }


    public void updateNotificationState(Notification notification)
    {
        notification.setSendingState("received");
        notificationRepo.save(notification);

    }


    public List<Notification> getByContentAndState(String content , String state)
    {
     //   return notificationRepo.findAllByMessageAndSendingState(message , state);

      return notificationRepo.findAll(NotificationSpec.getNotificationByContentAndState(content ,state));
    }
}
