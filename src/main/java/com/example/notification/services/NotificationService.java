package com.example.notification.services;


import com.example.notification.Repositories.NotificationRepo;
import com.example.notification.models.Notification;
import com.example.notification.models.NotificationStructure;
import com.example.notification.models.Receiver;
import com.example.notification.specifications.NotificationSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

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


    public Page<Notification> getByContentAndState(String content , String state , Integer page , Integer pageSize , String sortingAttrib )
    {
        //System.out.println("default case");
        return notificationRepo.findAll(NotificationSpec.getNotificationByContentAndState(content ,state) ,PageRequest.of(page,pageSize,Sort.Direction.ASC,sortingAttrib));
    }

    public Page<Notification> getByContentAndStateFromDateAndTime(String content ,String state ,Integer page ,Integer pageSize ,String sortingAttrib , String from)
    {
       // System.out.println("case 1");
        return notificationRepo.findAll(NotificationSpec.getNotificationByContentAndStateFromDateAndTime(content , state , from),PageRequest.of(page,pageSize,Sort.Direction.ASC,sortingAttrib));
    }

    public Page<Notification> getByContentAndStateToDateAndTime(String content ,String state ,Integer page ,Integer pageSize ,String sortingAttrib , String toDate)
    {
        //System.out.println("case 2");
        return notificationRepo.findAll(NotificationSpec.getNotificationByContentAndStateToDateAndTime(content , state , toDate),PageRequest.of(page,pageSize,Sort.Direction.ASC,sortingAttrib));
    }

    public Page<Notification> getByContentAndStateFromAndToDateAndTime(String content ,String state ,Integer page ,Integer pageSize ,String sortingAttrib , String fromDate,String toDate)
    {
       // System.out.println("case 3");
        return notificationRepo.findAll(NotificationSpec.getNotificationByContentAndStateFromAndToDateAndTime(content , state ,fromDate , toDate ),PageRequest.of(page,pageSize,Sort.Direction.ASC,sortingAttrib));
    }
}
