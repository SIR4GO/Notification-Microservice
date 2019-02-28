package com.example.notification.helpers;


import com.example.notification.models.NotificationStructure;
import com.example.notification.models.Receiver;

public class NotificationValidation {


    public static Boolean validateNotificationStructure(NotificationStructure notificationStructure){

        if(notificationStructure.getMessage().replaceAll("\\s","").isEmpty())
             throw new NotificationException(" message must not be empty ");
        if(notificationStructure.getFrom().replaceAll("\\s","").isEmpty())
             throw new NotificationException(" from must not be empty ");
        if(notificationStructure.getReceivers().isEmpty())
             throw new NotificationException(" receiver list must not be empty ");

        for (Receiver receiver : notificationStructure.getReceivers())
        {
            if(receiver.getUsername().replaceAll("\\s","").isEmpty())
                throw new NotificationException(" receiver username must not be empty ");

        }

        return true;

    }


}
